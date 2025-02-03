package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SearchCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) : BaseUseCase<List<CoinUiModel>>() {
    fun execute(query: String): Flow<UiState<List<CoinUiModel>>> {
        return super.execute {
            coinRepository.searchCoins(query).first()
        }
    }
}