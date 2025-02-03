package com.herdal.bitcointicker.features.coin.data.remote.datasource

import com.herdal.bitcointicker.core.data.remote.ApiExecutor
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinDetailDto
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinsResponse
import com.herdal.bitcointicker.features.coin.data.remote.service.CoinService
import javax.inject.Inject

class CoinRemoteDataSourceImpl @Inject constructor(
    private val coinService: CoinService
) : ApiExecutor, CoinRemoteDataSource {
    override suspend fun getCoins(): IResult<CoinsResponse> {
        return executeApiCall { coinService.getCoins() }
    }

    override suspend fun getCoinDetail(id: String): IResult<CoinDetailDto> {
        return executeApiCall { coinService.getCoinDetail(id) }
    }
}