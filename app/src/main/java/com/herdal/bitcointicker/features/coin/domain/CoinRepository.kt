package com.herdal.bitcointicker.features.coin.domain

import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoins(): IResult<List<CoinUiModel>>
    suspend fun getCoinDetail(id: String): IResult<CoinDetailUiModel>
    suspend fun getFavoriteCoins(): IResult<List<FavoriteCoinUiModel>>
    suspend fun addCoinToFavorites(coin: FavoriteCoinUiModel): IResult<Unit>
    suspend fun deleteCoinFromFavorites(coinId: String): IResult<Unit>
    fun searchCoins(query: String): Flow<IResult<List<CoinUiModel>>>
}