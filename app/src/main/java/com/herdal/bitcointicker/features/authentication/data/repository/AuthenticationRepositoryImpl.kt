package com.herdal.bitcointicker.features.authentication.data.repository

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.di.IoDispatcher
import com.herdal.bitcointicker.features.authentication.data.remote.datasource.AuthenticationDataSource
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {
    override suspend fun registerUser(email: String, password: String): FirebaseUser? =
        withContext(ioDispatcher) {
            authenticationDataSource.registerUser(email, password)
        }

    override suspend fun loginUser(email: String, password: String): FirebaseUser? =
        withContext(ioDispatcher) {
            authenticationDataSource.loginUser(email, password)
        }

    override suspend fun checkIfEmailExists(email: String): Boolean =
        withContext(ioDispatcher) {
            authenticationDataSource.checkIfEmailExists(email)
        }
}