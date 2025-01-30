package com.herdal.bitcointicker.features.coin.data.local.datasource

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.features.coin.data.local.dao.CoinDao
import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.herdal.bitcointicker.core.di.IoDispatcher
import javax.inject.Inject

class CoinLocalDataSourceImpl @Inject constructor(
    private val coinDao: CoinDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoinLocalDataSource {
    override suspend fun insertCoins(coins: List<CoinEntity>): IResult<Boolean> {
        return withContext(ioDispatcher) {
            try {
                coinDao.insertAllCoins(*coins.toTypedArray())
                IResult.Success(true)
            } catch (e: Exception) {
                IResult.Failure(
                    Error.RoomDatabaseError.QueryError(
                        e.localizedMessage ?: "Error while inserting coins"
                    )
                )
            }
        }
    }

    override fun searchCoins(query: String): Flow<IResult<List<CoinEntity>>> = flow {
        try {
            coinDao.searchCoins("%$query%").collect { result ->
                emit(IResult.Success(result))
            }
        } catch (e: Exception) {
            emit(
                IResult.Failure(
                    Error.RoomDatabaseError.QueryError(
                        e.localizedMessage ?: "Error while searching coins"
                    )
                )
            )
        }
    }
}