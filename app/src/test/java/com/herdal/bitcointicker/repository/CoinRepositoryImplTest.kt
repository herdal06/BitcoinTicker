package com.herdal.bitcointicker.repository

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.authentication.data.firebase.datasource.AuthenticationDataSource
import com.herdal.bitcointicker.features.coin.data.firebase.datasource.CoinFirebaseDataSource
import com.herdal.bitcointicker.features.coin.data.firebase.model.FavoriteCoinFirebaseModel
import com.herdal.bitcointicker.features.coin.data.firebase.model.toDomain
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSource
import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity
import com.herdal.bitcointicker.features.coin.data.local.entity.toDomain
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSource
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinDetailDto
import com.herdal.bitcointicker.features.coin.data.remote.dto.toDomain
import com.herdal.bitcointicker.features.coin.data.repository.CoinRepositoryImpl
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.toFirebaseModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CoinRepositoryImplTest {
    private lateinit var repository: CoinRepositoryImpl
    private lateinit var remoteDataSource: CoinRemoteDataSource
    private lateinit var localDataSource: CoinLocalDataSource
    private lateinit var authDataSource: AuthenticationDataSource
    private lateinit var firebaseDataSource: CoinFirebaseDataSource

    private val testUserId = "3z1K6XOWU7dvmtIQrVTW3GaF7np2"
    private val testCoinId = "bitcoin"

    @Before
    fun setup() {
        remoteDataSource = mockk(relaxed = true)
        localDataSource = mockk(relaxed = true)
        authDataSource = mockk(relaxed = true)
        firebaseDataSource = mockk(relaxed = true)

        repository = CoinRepositoryImpl(
            coinRemoteDataSource = remoteDataSource,
            coinLocalDataSource = localDataSource,
            authenticationDataSource = authDataSource,
            coinFirebaseDataSource = firebaseDataSource
        )
    }


    @Test
    fun `getCoinDetail when all success should return Success with isFavorite`() = runTest {
        // Given
        val user = mockk<FirebaseUser>(relaxed = true)
        val coinDetailDto = mockk<CoinDetailDto>(relaxed = true)
        val coinDetailUiModel = CoinDetailUiModel(
            id = testCoinId,
            name = "Bitcoin",
            symbol = "BTC",
            isFavorite = false
        )

        every { user.uid } returns testUserId
        every { coinDetailDto.toDomain() } returns coinDetailUiModel

        coEvery { authDataSource.getCurrentUser() } returns IResult.Success(user)
        coEvery { remoteDataSource.getCoinDetail(testCoinId) } returns IResult.Success(coinDetailDto)
        coEvery {
            firebaseDataSource.isCoinFavorite(
                testUserId,
                testCoinId
            )
        } returns IResult.Success(true)

        // When
        val result = repository.getCoinDetail(testCoinId)

        // Then
        assertTrue(result is IResult.Success)
        assertEquals(testCoinId, (result as IResult.Success).data.id)
        assertTrue(result.data.isFavorite)
    }

    @Test
    fun `getFavoriteCoins when successful should return Success`() = runTest {
        // Given
        val user = mockk<FirebaseUser>(relaxed = true)
        val favoriteModel = mockk<FavoriteCoinFirebaseModel>(relaxed = true)
        val uiModel = FavoriteCoinUiModel(id = testCoinId, name = "Bitcoin")

        every { user.uid } returns testUserId
        every { favoriteModel.toDomain() } returns uiModel

        coEvery { authDataSource.getCurrentUser() } returns IResult.Success(user)
        coEvery { firebaseDataSource.getFavoriteCoins(testUserId) } returns IResult.Success(
            listOf(
                favoriteModel
            )
        )

        // When
        val result = repository.getFavoriteCoins()

        // Then
        assertTrue(result is IResult.Success)
        assertEquals(1, (result as IResult.Success).data.size)
        assertEquals(testCoinId, result.data.first().id)
    }

    @Test
    fun `addCoinToFavorites when successful should return Success`() = runTest {
        // Given
        val user = mockk<FirebaseUser>(relaxed = true)
        val uiModel = FavoriteCoinUiModel(id = testCoinId, name = "Bitcoin")
        val firebaseModel = mockk<FavoriteCoinFirebaseModel>(relaxed = true)

        every { user.uid } returns testUserId
        every { uiModel.toFirebaseModel() } returns firebaseModel

        coEvery { authDataSource.getCurrentUser() } returns IResult.Success(user)
        coEvery {
            firebaseDataSource.addCoinToFavorites(
                testUserId,
                firebaseModel
            )
        } returns IResult.Success(Unit)

        // When
        val result = repository.addCoinToFavorites(uiModel)

        // Then
        assertTrue(result is IResult.Success)
    }

    @Test
    fun `searchCoins when successful should return Success`() = runTest {
        // Given
        val query = "bitcoin"
        val entity = mockk<CoinEntity>(relaxed = true)
        val uiModel = CoinUiModel(id = testCoinId, name = "Bitcoin", symbol = "BTC")

        every { entity.toDomain() } returns uiModel
        every { localDataSource.searchCoins(query) } returns flowOf(IResult.Success(listOf(entity)))

        // When
        val result = repository.searchCoins(query).first()

        // Then
        assertTrue(result is IResult.Success)
        assertEquals(1, (result as IResult.Success).data.size)
        assertEquals(testCoinId, result.data.first().id)
    }

    @Test
    fun `getCoins when remote fails should return Failure`() = runTest {
        // Given
        val error = Error.NetworkError.NetworkUnavailable("No internet")
        coEvery { remoteDataSource.getCoins() } returns IResult.Failure(error)

        // When
        val result = repository.getCoins()

        // Then
        assertTrue(result is IResult.Failure)
        assertEquals(error, (result as IResult.Failure).error)
    }
}