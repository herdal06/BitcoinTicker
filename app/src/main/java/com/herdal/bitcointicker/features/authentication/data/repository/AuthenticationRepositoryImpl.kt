package com.herdal.bitcointicker.features.authentication.data.repository

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.data.local.PreferencesManager
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.core.di.IoDispatcher
import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.features.authentication.data.firebase.datasource.AuthenticationDataSource
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import com.herdal.bitcointicker.features.user.data.firebase.datasource.UserFirebaseDataSource
import com.herdal.bitcointicker.features.user.data.firebase.model.UserFirebaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    private val userFirebaseDataSource: UserFirebaseDataSource,
    private val preferencesManager: PreferencesManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {
    override suspend fun registerUser(email: String, password: String): IResult<FirebaseUser> =
        withContext(ioDispatcher) {
            when (val result = authenticationDataSource.registerUser(email, password)) {
                is IResult.Success -> {
                    val user = UserFirebaseModel(
                        id = result.data.uid,
                        email = result.data.email.orEmpty(),
                        createdAt = System.currentTimeMillis()
                    )
                    preferencesManager.setUserLoggedIn(true)
                    when (val saveResult = userFirebaseDataSource.saveUser(user)) {
                        is IResult.Success -> IResult.Success(result.data)
                        is IResult.Failure -> IResult.Failure(saveResult.error)
                    }
                }

                is IResult.Failure -> IResult.Failure(result.error)
            }
        }

    override suspend fun loginUser(email: String, password: String): IResult<FirebaseUser> =
        withContext(ioDispatcher) {
            when (val result = authenticationDataSource.loginUser(email, password)) {
                is IResult.Success -> {
                    preferencesManager.setUserLoggedIn(true)
                    when (val userResult = userFirebaseDataSource.getUser(result.data.uid)) {
                        is IResult.Success -> IResult.Success(result.data)
                        is IResult.Failure -> {
                            if (userResult.error is Error.FirestoreError.DocumentNotFound) {
                                val user = UserFirebaseModel(
                                    id = result.data.uid,
                                    email = result.data.email.orEmpty(),
                                    createdAt = System.currentTimeMillis()
                                )
                                when (val saveResult = userFirebaseDataSource.saveUser(user)) {
                                    is IResult.Success -> {
                                        IResult.Success(result.data)
                                    }

                                    is IResult.Failure -> {
                                        IResult.Failure(saveResult.error)
                                    }
                                }
                            } else {
                                IResult.Failure(userResult.error)
                            }
                        }
                    }
                }

                is IResult.Failure -> IResult.Failure(result.error)
            }
        }

    override suspend fun checkIfEmailExists(email: String): IResult<Boolean> =
        withContext(ioDispatcher) {
            authenticationDataSource.checkIfEmailExists(email)
        }

    override suspend fun getCurrentUser(): IResult<FirebaseUser> =
        withContext(ioDispatcher) {
            authenticationDataSource.getCurrentUser()
        }

    override suspend fun signOut(): IResult<Unit> =
        withContext(ioDispatcher) {
            when (val result = authenticationDataSource.signOut()) {
                is IResult.Failure ->  IResult.Failure(result.error)
                is IResult.Success -> IResult.Success(result.data)
            }
        }
}