package com.herdal.bitcointicker.features.coin.domain

import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel

interface CoinRepository {
    suspend fun getCoins(currency: String): IResult<List<CoinUiModel>>
    suspend fun getCoinDetail(id: String): IResult<CoinDetailUiModel>
}