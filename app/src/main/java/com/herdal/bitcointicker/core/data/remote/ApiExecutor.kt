package com.herdal.bitcointicker.core.data.remote

import okio.IOException
import retrofit2.Response
import java.net.UnknownHostException
import org.json.JSONObject

interface ApiExecutor {
    suspend fun <T> executeApiCall(apiCall: suspend () -> Response<T>): IResult<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let {
                    IResult.Success(it)
                } ?: IResult.Failure(Error.NetworkError.UnknownError("Response body is null"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorBody) ?: response.message()

                IResult.Failure(Error.NetworkError.HttpError(response.code(), errorMessage))
            }

        } catch (e: IOException) {
            IResult.Failure(Error.NetworkError.IOError(e.localizedMessage ?: "Network error"))
        } catch (e: UnknownHostException) {
            IResult.Failure(Error.NetworkError.NetworkUnavailable(e.localizedMessage ?: "Network error"))
        } catch (e: Exception) {
            IResult.Failure(Error.NetworkError.UnknownError(e.localizedMessage ?: "Unknown error"))
        }
    }

    private fun extractErrorMessage(errorBody: String?): String? {
        return try {
            errorBody?.let {
                val jsonObject = JSONObject(it)
                jsonObject.optJSONObject("status")?.optString("error_message")
            }
        } catch (e: Exception) {
            null
        }
    }
}