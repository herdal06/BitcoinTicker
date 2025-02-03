package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCoinFromFavoritesUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) : BaseUseCase<Unit>() {
    fun execute(coinId: String): Flow<UiState<Unit>> {
        return super.execute {
            coinRepository.deleteCoinFromFavorites(coinId)
        }
    }
}