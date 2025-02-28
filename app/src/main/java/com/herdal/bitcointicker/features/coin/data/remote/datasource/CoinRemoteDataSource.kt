package com.herdal.bitcointicker.features.coin.data.remote.datasource

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinDetailDto
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinsResponse

interface CoinRemoteDataSource {
    suspend fun getCoins(): IResult<CoinsResponse>
    suspend fun getCoinDetail(id: String): IResult<CoinDetailDto>
}