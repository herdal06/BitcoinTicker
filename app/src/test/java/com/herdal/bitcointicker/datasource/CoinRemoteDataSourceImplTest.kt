package com.herdal.bitcointicker.datasource

import com.herdal.bitcointicker.core.data.remote.Error
import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSourceImpl
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinDetailDto
import com.herdal.bitcointicker.features.coin.data.remote.dto.CoinsResponse
import com.herdal.bitcointicker.features.coin.data.remote.service.CoinService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CoinRemoteDataSourceImplTest {
    private lateinit var coinRemoteDataSource: CoinRemoteDataSourceImpl
    private lateinit var coinService: CoinService

    @Before
    fun setup() {
        coinService = mockk()
        coinRemoteDataSource = CoinRemoteDataSourceImpl(coinService)
    }

    @Test
    fun `getCoins when successful should return Success`() = runTest {
        // Given
        val mockResponse = mockk<CoinsResponse>()
        coEvery { coinService.getCoins() } returns Response.success(mockResponse)

        // When
        val result = coinRemoteDataSource.getCoins()

        // Then
        assertTrue(result is IResult.Success)
        assertEquals(mockResponse, (result as IResult.Success).data)
    }

    @Test
    fun `getCoins when response body is null should return Failure`() = runTest {
        // Given
        coEvery { coinService.getCoins() } returns Response.success(null)

        // When
        val result = coinRemoteDataSource.getCoins()

        // Then
        assertTrue(result is IResult.Failure)
        assertTrue((result as IResult.Failure).error is Error.NetworkError.UnknownError)
        assertEquals(
            "Response body is null",
            (result.error as Error.NetworkError.UnknownError).message
        )
    }

    @Test
    fun `getCoins when IOException should return NetworkError`() = runTest {
        // Given
        coEvery { coinService.getCoins() } throws IOException("Network error occurred")

        // When
        val result = coinRemoteDataSource.getCoins()

        // Then
        assertTrue(result is IResult.Failure)
        assertTrue((result as IResult.Failure).error is Error.NetworkError.IOError)
    }

    @Test
    fun `getCoinDetail when successful should return Success`() = runTest {
        // Given
        val mockResponse = mockk<CoinDetailDto>()
        coEvery { coinService.getCoinDetail(any()) } returns Response.success(mockResponse)

        // When
        val result = coinRemoteDataSource.getCoinDetail("bitcoin")

        // Then
        assertTrue(result is IResult.Success)
        assertEquals(mockResponse, (result as IResult.Success).data)
    }
}