package com.herdal.bitcointicker.features.coin.data.remote.datasource

import com.herdal.bitcointicker.core.data.remote.Result
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinsResponse

interface CoinRemoteDataSource {
    suspend fun getCoins(currency: String): Result<CoinsResponse>
}