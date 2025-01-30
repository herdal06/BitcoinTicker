package com.herdal.bitcointicker.features.coin.data.repository

import com.herdal.bitcointicker.core.data.local.DatabaseResult
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSource
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSource
import com.herdal.bitcointicker.core.data.remote.ApiResult
import com.herdal.bitcointicker.core.data.remote.NetworkException
import com.herdal.bitcointicker.features.coin.data.remote.dto.toDomain
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.toEntity
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource
) : CoinRepository {
    override suspend fun getCoins(currency: String): ApiResult<List<CoinUiModel>> {
        return when (val remoteResult = coinRemoteDataSource.getCoins(currency)) {
            is ApiResult.Success -> {
                val coins = remoteResult.data.map { it.toDomain() }

                val insertResult = coinLocalDataSource.insertCoins(
                    coins.map { it.toEntity() }
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