package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddCoinToFavoritesUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) : BaseUseCase<Unit>() {
    fun execute(coin: FavoriteCoinUiModel): Flow<UiState<Unit>> {
        return super.execute {
            coinRepository.addCoinToFavorites(coin)
        }
    }
}