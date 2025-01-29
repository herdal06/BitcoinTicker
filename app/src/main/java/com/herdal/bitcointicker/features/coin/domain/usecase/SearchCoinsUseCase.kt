package com.herdal.bitcointicker.features.coin.domain.usecase

import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import javax.inject.Inject

class SearchCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
}