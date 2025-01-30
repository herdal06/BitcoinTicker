package com.herdal.bitcointicker.features.authentication.data.remote.datasource

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.SignInMethodQueryResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthenticationDataSource {
    override suspend fun registerUser(email: String, password: String): FirebaseUser {
        val currentUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return currentUser.user ?: throw Exception()
    }

    override suspend fun loginUser(email: String, password: String): FirebaseUser {
        val currentUser = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return currentUser.user ?: throw Exception()
    }

    override suspend fun checkIfEmailExists(email: String): Boolean {
        val task: Task<SignInMethodQueryResult> = firebaseAuth.fetchSignInMethodsForEmail(email)
        val result: SignInMethodQueryResult = Tasks.await(task)
        val signInMethods = result.signInMethods
        return signInMethods.isNullOrEmpty().not()
    }
}