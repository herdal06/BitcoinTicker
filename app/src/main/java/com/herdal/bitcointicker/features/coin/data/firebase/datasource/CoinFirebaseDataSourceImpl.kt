package com.herdal.bitcointicker.features.coin.data.firebase.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.firebase.model.FavoriteCoinFirebaseModel
import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.core.util.logE
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CoinFirebaseDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CoinFirebaseDataSource {
    override suspend fun getFavoriteCoins(userId: String): IResult<List<FavoriteCoinFirebaseModel>> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()

            val favoriteCoins = snapshot.documents.mapNotNull { doc ->
                doc.toObject(FavoriteCoinFirebaseModel::class.java)?.copy(id = doc.id)
            }
            IResult.Success(favoriteCoins)
        } catch (e: Exception) {
            logE(e.message.toString())
            IResult.Failure(Error.FirestoreError.UnknownFirestoreError(e.message.orEmpty()))
        }
    }

    override suspend fun addCoinToFavorites(
        userId: String,
        coin: FavoriteCoinFirebaseModel
    ): IResult<Unit> {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(coin.id ?: "")
                .set(coin.copy(addedAt = System.currentTimeMillis()))
                .await()

            IResult.Success(Unit)
        } catch (e: Exception) {
            IResult.Failure(Error.FirestoreError.WriteError(e.message.orEmpty()))
        }
    }

    override suspend fun deleteCoinFromFavorites(userId: String, coinId: String): IResult<Unit> {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(coinId)
                .delete()
                .await()

            IResult.Success(Unit)
        } catch (e: Exception) {
            IResult.Failure(Error.FirestoreError.WriteError(e.message.orEmpty()))
        }
    }

    override suspend fun isCoinFavorite(userId: String, coinId: String): IResult<Boolean> {
        return try {
            val docSnapshot = firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(coinId)
                .get()
                .await()

            IResult.Success(docSnapshot.exists())
        } catch (e: Exception) {
            IResult.Failure(Error.FirestoreError.UnknownFirestoreError(e.message.orEmpty()))
        }
    }
}