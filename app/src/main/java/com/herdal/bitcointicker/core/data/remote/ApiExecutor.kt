package com.herdal.bitcointicker.core.data.remote

import okio.IOException
import retrofit2.Response
import java.net.UnknownHostException

interface ApiExecutor {
    suspend fun <T> executeApiCall(apiCall: suspend () -> Response<T>): IResult<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let {
                    IResult.Success(it)
                } ?: IResult.Failure(Error.NetworkError.UnknownError("Response body is null"))
            } else {
                IResult.Failure(Error.NetworkError.HttpError(response.code(), response.message()))
            }

        } catch (e: IOException) {
            IResult.Failure(
                Error.NetworkError.NetworkUnavailable(
                    e.localizedMessage ?: "Network error"
                )
            )
        } catch (e: UnknownHostException) {
            IResult.Failure(
                Error.NetworkError.NetworkUnavailable(
                    e.localizedMessage ?: "Network error"
                )
            )
        } catch (e: Exception) {
            IResult.Failure(Error.NetworkError.UnknownError(e.localizedMessage ?: "Unknown error"))
        }
    }
}