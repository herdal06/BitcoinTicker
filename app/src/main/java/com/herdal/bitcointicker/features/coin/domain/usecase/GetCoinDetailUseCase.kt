package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    fun execute(id: String): Flow<UiState<CoinDetailUiModel>> = flow {
        emit(UiState.Loading)
        try {
            when (val result = coinRepository.getCoinDetail(id)) {
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