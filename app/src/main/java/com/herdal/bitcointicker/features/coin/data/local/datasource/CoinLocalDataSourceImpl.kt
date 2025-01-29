package com.herdal.bitcointicker.features.coin.data.local.datasource

import com.herdal.bitcointicker.core.data.local.DatabaseException
import com.herdal.bitcointicker.core.data.local.DatabaseResult
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
    override suspend fun insertCoins(coins: List<CoinEntity>): DatabaseResult<Boolean> {
        return withContext(ioDispatcher) {
            try {
                coinDao.insertAllCoins(*coins.toTypedArray())
                DatabaseResult.Success(true)
            } catch (e: Exception) {
                DatabaseResult.Error(
                    DatabaseException.UnknownError(
                        e.localizedMessage ?: "Unknown error"
                    )
                )
            }
        }
    }

    override fun searchCoins(query: String): Flow<DatabaseResult<List<CoinEntity>>> = flow {
        try {
            coinDao.searchCoins("%$query%").collect { result ->
                emit(DatabaseResult.Success(result))
            }
        } catch (e: Exception) {
            emit(
                DatabaseResult.Error(
                    DatabaseException.UnknownError(
                        e.localizedMessage ?: "Unknown error"
                    )
                )
            )
        }
    }
}