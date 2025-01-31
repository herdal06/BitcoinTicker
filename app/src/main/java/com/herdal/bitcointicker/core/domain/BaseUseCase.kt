package com.herdal.bitcointicker.core.domain

import com.herdal.bitcointicker.core.data.remote.IResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class BaseUseCase<T> @Inject constructor() {
    open fun execute(action: suspend () -> IResult<T>): Flow<UiState<T>> = flow {
        emit(UiState.Loading)
        try {
            when (val result = action()) {
                is IResult.Success -> {
                    emit(UiState.Success(result.data))
                }
                is IResult.Failure -> {
                    emit(UiState.Error(result.error.toString()))
                }
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message.orEmpty()))
        }
    }
}