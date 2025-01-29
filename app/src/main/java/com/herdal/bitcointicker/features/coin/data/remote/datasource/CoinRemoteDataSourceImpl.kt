package com.herdal.bitcointicker.features.coin.data.remote.datasource

import com.herdal.bitcointicker.core.data.remote.ApiExecutor
import com.herdal.bitcointicker.core.data.remote.ApiResult
import com.herdal.bitcointicker.core.data.remote.NetworkHelper
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinsResponse
import com.herdal.bitcointicker.features.coin.data.remote.service.CoinService

class CoinRemoteDataSourceImpl(
    private val coinService: CoinService,
    networkHelper: NetworkHelper
) : ApiExecutor(networkHelper), CoinRemoteDataSource {
    override suspend fun getCoins(currency: String): ApiResult<CoinsResponse> {
        return executeApiCall { coinService.getCoins(currency) }
    }
}