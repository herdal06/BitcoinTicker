package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) : BaseUseCase<List<FavoriteCoinUiModel>>() {
    fun execute(): Flow<UiState<List<FavoriteCoinUiModel>>> {
        return super.execute {
            coinRepository.getFavoriteCoins()
        }
    }
}