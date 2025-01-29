package com.herdal.bitcointicker.core.data.remote

import okio.IOException
import retrofit2.Response

abstract class ApiExecutor(private val networkHelper: NetworkHelper) {
    suspend fun <T> executeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            if (!networkHelper.isConnected()) {
                return ApiResult.Error(NetworkException.NoInternet)
            }

            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error(NetworkException.UnknownError("Response body is null"))
            } else {
                ApiResult.Error(NetworkException.HttpError(response.code(), response.message()))
            }

        } catch (e: IOException) {
            ApiResult.Error(NetworkException.NetworkError(e.localizedMessage ?: "Network error"))
        } catch (e: Exception) {
            ApiResult.Error(NetworkException.UnknownError(e.localizedMessage ?: "Unknown error"))
        }
    }
}