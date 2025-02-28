package com.herdal.bitcointicker.features.coin.data.repository

import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.authentication.data.firebase.datasource.AuthenticationDataSource
import com.herdal.bitcointicker.features.coin.data.firebase.datasource.CoinFirebaseDataSource
import com.herdal.bitcointicker.features.coin.data.firebase.model.toDomain
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSource
import com.herdal.bitcointicker.features.coin.data.local.entity.toDomain
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSource
import com.herdal.bitcointicker.features.coin.data.remote.dto.toDomain
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.toEntity
import com.herdal.bitcointicker.features.coin.domain.uimodel.toFirebaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource,
    private val authenticationDataSource: AuthenticationDataSource,
    private val coinFirebaseDataSource: CoinFirebaseDataSource
) : CoinRepository {
    override suspend fun getCoins(): IResult<List<CoinUiModel>> {
        return when (val remoteResult = coinRemoteDataSource.getCoins()) {
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
        return when (val currentUserResult = authenticationDataSource.getCurrentUser()) {
            is IResult.Success -> {
                val userId = currentUserResult.data.uid

                when (val detailResult = coinRemoteDataSource.getCoinDetail(id)) {
                    is IResult.Success -> {
                        val isFavorite = when (val favoriteResult =
                            coinFirebaseDataSource.isCoinFavorite(userId, id)) {
                            is IResult.Success -> favoriteResult.data
                            is IResult.Failure -> false
                        }

                        IResult.Success(detailResult.data.toDomain().copy(isFavorite = isFavorite))
                    }

                    is IResult.Failure -> IResult.Failure(detailResult.error)
                }
            }

            is IResult.Failure -> IResult.Failure(currentUserResult.error)
        }
    }

    override suspend fun getFavoriteCoins(): IResult<List<FavoriteCoinUiModel>> {
        return when (val currentUserResult = authenticationDataSource.getCurrentUser()) {
            is IResult.Success -> {
                val userId = currentUserResult.data.uid
                when (val firebaseResult = coinFirebaseDataSource.getFavoriteCoins(userId)) {
                    is IResult.Success -> {
                        val favoriteCoinsUiModels = firebaseResult.data.map { it.toDomain() }
                        IResult.Success(favoriteCoinsUiModels)
                    }

                    is IResult.Failure -> IResult.Failure(firebaseResult.error)
                }
            }

            is IResult.Failure -> IResult.Failure(currentUserResult.error)
        }
    }

    override suspend fun addCoinToFavorites(coin: FavoriteCoinUiModel): IResult<Unit> {
        return when (val currentUserResult = authenticationDataSource.getCurrentUser()) {
            is IResult.Success -> {
                val userId = currentUserResult.data.uid
                coinFirebaseDataSource.addCoinToFavorites(userId, coin.toFirebaseModel())
            }

            is IResult.Failure -> IResult.Failure(currentUserResult.error)
        }
    }

    override suspend fun deleteCoinFromFavorites(coinId: String): IResult<Unit> {
        return when (val currentUserResult = authenticationDataSource.getCurrentUser()) {
            is IResult.Success -> {
                val userId = currentUserResult.data.uid
                coinFirebaseDataSource.deleteCoinFromFavorites(userId, coinId)
            }

            is IResult.Failure -> IResult.Failure(currentUserResult.error)
        }
    }

    override fun searchCoins(query: String): Flow<IResult<List<CoinUiModel>>> {
        return coinLocalDataSource.searchCoins(query).map { result ->
            when (result) {
                is IResult.Success -> {
                    IResult.Success(result.data.map { it.toDomain() })
                }

                is IResult.Failure -> {
                    IResult.Failure(result.error)
                }
            }
        }
    }
}