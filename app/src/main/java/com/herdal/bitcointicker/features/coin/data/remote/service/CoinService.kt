package com.herdal.bitcointicker.features.coin.data.remote.service

import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinService {
    @GET(END_POINT_GET_CRYPTO_CURRENCIES)
    suspend fun getCoins(@Query("vs_currency") currency: String = "usd"): Response<CoinsResponse>

    companion object {
        const val END_POINT_GET_CRYPTO_CURRENCIES = "api/v3/coins/markets"
    }
}