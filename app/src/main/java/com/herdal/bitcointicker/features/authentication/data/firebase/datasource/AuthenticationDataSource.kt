package com.herdal.bitcointicker.features.authentication.data.firebase.datasource

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.data.remote.IResult

interface AuthenticationDataSource {
    suspend fun registerUser(email: String, password: String): IResult<FirebaseUser>
    suspend fun loginUser(email: String, password: String): IResult<FirebaseUser>
    suspend fun checkIfEmailExists(email: String): IResult<Boolean>
    suspend fun getCurrentUser(): IResult<FirebaseUser>
    suspend fun signOut(): IResult<Unit>
    suspend fun isUserLoggedIn(): IResult<Boolean>
}