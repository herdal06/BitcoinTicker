package com.herdal.bitcointicker.features.authentication.domain.usecase

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckIfEmailExistsUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(email: String): Flow<UiState<Boolean>> = flow {
        emit(UiState.Loading)
        when (val result = authenticationRepository.checkIfEmailExists(email)) {
            is IResult.Success -> {
                emit(UiState.Success(result.data))
            }

            is IResult.Failure -> {
                emit(UiState.Error(result.error.toString()))
            }
        }
    }
}