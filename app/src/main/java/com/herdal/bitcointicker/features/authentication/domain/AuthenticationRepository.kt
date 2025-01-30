package com.herdal.bitcointicker.features.authentication.domain

import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {
    suspend fun registerUser(email: String, password: String): FirebaseUser?
    suspend fun loginUser(email: String, password: String): FirebaseUser?
    suspend fun checkIfEmailExists(email: String): Boolean
}