package com.herdal.bitcointicker.core.data.remote

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val error: NetworkException) : ApiResult<Nothing>()
}