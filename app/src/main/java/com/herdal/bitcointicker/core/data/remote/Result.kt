package com.herdal.bitcointicker.core.data.remote

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: NetworkException) : Result<Nothing>()
}