package com.herdal.bitcointicker.features.authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(email: String, password: String): Flow<UiState<FirebaseUser?>> = flow {
        //emit(UiState.Loading)
        try {
            val result = authenticationRepository.registerUser(email, password)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.orEmpty()))
        }
    }
}