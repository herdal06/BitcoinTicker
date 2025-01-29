package com.herdal.bitcointicker.features.coin.data.repository

import com.herdal.bitcointicker.core.data.local.DatabaseResult
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSource
import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSource
import com.herdal.bitcointicker.core.data.remote.ApiResult
import com.herdal.bitcointicker.core.data.remote.NetworkException
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource
) : CoinRepository {
    override suspend fun getCoins(currency: String): ApiResult<List<CoinUiModel>> {
        return when (val remoteResult = coinRemoteDataSource.getCoins(currency)) {
            is ApiResult.Success -> {
                val coins = remoteResult.data.map { coinResponse ->
                    CoinUiModel(
                        id = coinResponse.id,
                        name = coinResponse.name,
                        symbol = coinResponse.symbol
                    )
                }

                val insertResult = coinLocalDataSource.insertCoins(
                    coins.map {
                        CoinEntity(
                            coinId = it.id,
                            name = it.name,
                            symbol = it.symbol
                        )
                    }
                )

                when (insertResult) {
                    is DatabaseResult.Success -> ApiResult.Success(coins)
                    is DatabaseResult.Error -> ApiResult.Error(
                        NetworkException.UnknownError(
                            insertResult.exception.message.orEmpty()
                        )
                    )
                }
            }

            is ApiResult.Error -> {
                ApiResult.Error(remoteResult.error)
            }
        }
    }
}