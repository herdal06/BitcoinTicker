package com.herdal.bitcointicker.datasource

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.local.dao.CoinDao
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSourceImpl
import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import com.herdal.bitcointicker.core.data.remote.Error
import io.mockk.coVerify
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CoinLocalDataSourceImplTest {
    private lateinit var coinLocalDataSource: CoinLocalDataSourceImpl
    private lateinit var coinDao: CoinDao

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        coinDao = mockk()
        coinLocalDataSource = CoinLocalDataSourceImpl(
            coinDao = coinDao,
            ioDispatcher = testDispatcher
        )
    }

    @Test
    fun `insertCoins when successful should return Success with true`() = runTest {
        // Given
        val coins = listOf(
            CoinEntity(coinId = "1", name = "Bitcoin"),
            CoinEntity(coinId = "2", name = "Ethereum")
        )
        coEvery { coinDao.insertAllCoins(*coins.toTypedArray()) } just runs

        // When
        val result = coinLocalDataSource.insertCoins(coins)

        // Then
        assertTrue(result is IResult.Success)
        assertEquals(true, (result as IResult.Success).data)
        coVerify { coinDao.insertAllCoins(*coins.toTypedArray()) }
    }

    @Test
    fun `insertCoins when error occurs should return Failure`() = runTest {
        // Given
        val coins = listOf(
            CoinEntity(coinId = "1", name = "Bitcoin")
        )
        val errorMessage = "Database error"
        coEvery { coinDao.insertAllCoins(*coins.toTypedArray()) } throws Exception(errorMessage)

        // When
        val result = coinLocalDataSource.insertCoins(coins)

        // Then
        assertTrue(result is IResult.Failure)
        assertTrue((result as IResult.Failure).error is Error.RoomDatabaseError.QueryError)
        assertEquals(errorMessage, (result.error as Error.RoomDatabaseError.QueryError).message)
    }

    @Test
    fun `searchCoins when successful should return Success with list`() = runTest {
        // Given
        val query = "bit"
        val coins = listOf(
            CoinEntity(coinId = "1", name = "Bitcoin"),
            CoinEntity(coinId = "2", name = "BitTorrent")
        )
        every { coinDao.searchCoins("%$query%") } returns flowOf(coins)

        // When
        val results = mutableListOf<IResult<List<CoinEntity>>>()
        coinLocalDataSource.searchCoins(query).toList(results)

        // Then
        assertTrue(results.size == 1)
        assertTrue(results[0] is IResult.Success)
        assertEquals(coins, (results[0] as IResult.Success).data)
    }

    @Test
    fun `searchCoins when error occurs should return Failure`() = runTest {
        // Given
        val query = "bit"
        val errorMessage = "Database error"
        every { coinDao.searchCoins("%$query%") } throws Exception(errorMessage)

        // When
        val results = mutableListOf<IResult<List<CoinEntity>>>()
        coinLocalDataSource.searchCoins(query).toList(results)

        // Then
        assertTrue(results.size == 1)
        val result = results[0]
        assertTrue(result is IResult.Failure)
        val failure = result as IResult.Failure
        val queryError = failure.error as Error.RoomDatabaseError.QueryError
        assertEquals(errorMessage, queryError.message)
    }

    @Test
    fun `searchCoins should use correct query format`() = runTest {
        // Given
        val query = "bitcoin"
        val expectedQuery = "%bitcoin%"
        every { coinDao.searchCoins(expectedQuery) } returns flowOf(emptyList())

        // When
        coinLocalDataSource.searchCoins(query).collect { }

        // Then
        verify { coinDao.searchCoins(expectedQuery) }
    }
}