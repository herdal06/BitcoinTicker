package com.herdal.bitcointicker.features.user.data.firebase.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.user.data.firebase.model.UserFirebaseModel
import com.herdal.bitcointicker.core.data.remote.Error
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirebaseDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserFirebaseDataSource {
    override suspend fun saveUser(user: UserFirebaseModel): IResult<Unit> = try {
        user.id?.let {
            firestore.collection("users")
                .document(it)
                .set(user)
                .await()
        }
        IResult.Success(Unit)
    } catch (e: Exception) {
        IResult.Failure(Error.FirestoreError.WriteError(e.message.orEmpty()))
    }

    override suspend fun getUser(userId: String): IResult<UserFirebaseModel> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            val user = snapshot.toObject(UserFirebaseModel::class.java)
                ?: return IResult.Failure(Error.FirestoreError.DocumentNotFound("get user failed"))

            IResult.Success(user)
        } catch (e: Exception) {
            IResult.Failure(Error.FirestoreError.DocumentNotFound(e.message.orEmpty()))
        }
    }
}