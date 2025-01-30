package com.herdal.bitcointicker.features.authentication.data.remote.datasource

import com.google.firebase.auth.FirebaseUser

interface AuthenticationDataSource {
    suspend fun registerUser(email: String, password: String): FirebaseUser?
    suspend fun loginUser(email: String, password: String): FirebaseUser?
    suspend fun checkIfEmailExists(email: String): Boolean
}