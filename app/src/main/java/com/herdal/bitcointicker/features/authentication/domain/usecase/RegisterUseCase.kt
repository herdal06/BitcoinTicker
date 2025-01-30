package com.herdal.bitcointicker.features.authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(email: String, password: String): Flow<UiState<FirebaseUser?>> = flow {
        emit(UiState.Loading)
        when (val result = authenticationRepository.registerUser(email, password)) {
            is IResult.Success -> {
                emit(UiState.Success(result.data))
            }

            is IResult.Failure -> {
                emit(UiState.Error(result.error.toString()))
            }
        }
    }
}