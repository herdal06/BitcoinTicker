package com.herdal.bitcointicker.core.data.remote

import okio.IOException
import retrofit2.Response

abstract class ApiExecutor(private val networkHelper: NetworkHelper) {
    suspend fun <T> executeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            if (!networkHelper.isConnected()) {
                return Result.Error(NetworkException.NoInternet)
            }

            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(NetworkException.UnknownError("Response body is null"))
            } else {
                Result.Error(NetworkException.HttpError(response.code(), response.message()))
            }

        } catch (e: IOException) {
            Result.Error(NetworkException.NetworkError(e.localizedMessage ?: "Network error"))
        } catch (e: Exception) {
            Result.Error(NetworkException.UnknownError(e.localizedMessage ?: "Unknown error"))
        }
    }
}