package com.herdal.bitcointicker.features.coin.data.firebase.datasource

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.firebase.model.FavoriteCoinFirebaseModel

interface CoinFirebaseDataSource {
    suspend fun getFavoriteCoins(userId: String): IResult<List<FavoriteCoinFirebaseModel>>
    suspend fun addCoinToFavorites(userId: String, coin: FavoriteCoinFirebaseModel): IResult<Unit>
    suspend fun deleteCoinFromFavorites(userId: String, coinId: String): IResult<Unit>
    suspend fun isCoinFavorite(userId: String, coinId: String): IResult<Boolean>
}