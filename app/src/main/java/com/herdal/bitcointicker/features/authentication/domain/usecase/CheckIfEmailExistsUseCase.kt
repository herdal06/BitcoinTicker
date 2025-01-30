package com.herdal.bitcointicker.features.authentication.domain.usecase

import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckIfEmailExistsUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(email: String): Flow<UiState<Boolean>> = flow {
        //emit(UiState.Loading)
        try {
            val result = authenticationRepository.checkIfEmailExists(email)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e.message.orEmpty()))
        }
    }
}