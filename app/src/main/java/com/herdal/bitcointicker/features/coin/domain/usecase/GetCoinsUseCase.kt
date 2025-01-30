package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    fun execute(currency: String): Flow<UiState<List<CoinUiModel>>> = flow {
        emit(UiState.Loading)
        try {
            when (val result = coinRepository.getCoins(currency)) {
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