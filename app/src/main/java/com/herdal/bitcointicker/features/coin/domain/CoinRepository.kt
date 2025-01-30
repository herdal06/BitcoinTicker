package com.herdal.bitcointicker.features.coin.domain

import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import com.herdal.bitcointicker.core.data.remote.ApiResult
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel

interface CoinRepository {
    suspend fun getCoins(currency: String): ApiResult<List<CoinUiModel>>
    suspend fun getCoinDetail(id: String): ApiResult<CoinDetailUiModel>
}