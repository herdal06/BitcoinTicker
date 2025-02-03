package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) : BaseUseCase<CoinDetailUiModel>() {
    fun execute(id: String): Flow<UiState<CoinDetailUiModel>> {
        return super.execute {
            coinRepository.getCoinDetail(id)
        }
    }
}