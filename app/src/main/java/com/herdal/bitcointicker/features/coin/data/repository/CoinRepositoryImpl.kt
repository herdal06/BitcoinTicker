package com.herdal.bitcointicker.features.coin.data.repository

import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSource
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSource
import com.herdal.bitcointicker.features.coin.data.remote.dto.toDomain
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.toEntity
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource
) : CoinRepository {
    override suspend fun getCoins(currency: String): IResult<List<CoinUiModel>> {
        return when (val remoteResult = coinRemoteDataSource.getCoins(currency)) {
            is IResult.Success -> {
                val coins = remoteResult.data.map { it.toDomain() }

                val insertResult = coinLocalDataSource.insertCoins(
                    coins.map { it.toEntity() }
                )

                when (insertResult) {
                    is IResult.Success -> IResult.Success(coins)
                    is IResult.Failure -> IResult.Failure(
                        Error.RoomDatabaseError.QueryError(
                            insertResult.error.toString()
                        )
                    )
                }
            }

            is IResult.Failure -> {
                IResult.Failure(remoteResult.error)
            }
        }
    }

    override suspend fun getCoinDetail(id: String): IResult<CoinDetailUiModel> {
        return when (val result = coinRemoteDataSource.getCoinDetail(id)) {
            is IResult.Success -> {
                IResult.Success(result.data.toDomain())
            }

            is IResult.Failure -> {
                IResult.Failure(result.error)
            }
        }
    }
}