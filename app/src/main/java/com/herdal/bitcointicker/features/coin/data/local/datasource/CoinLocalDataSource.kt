package com.herdal.bitcointicker.features.coin.data.local.datasource

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity
import kotlinx.coroutines.flow.Flow

interface CoinLocalDataSource {
    suspend fun insertCoins(coins: List<CoinEntity>): IResult<Boolean>
    fun searchCoins(query: String): Flow<IResult<List<CoinEntity>>>
}