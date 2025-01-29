package com.herdal.bitcointicker.core.data.remote

sealed class NetworkException {
    data class NetworkError(val message: String) : NetworkException()
    data class HttpError(val code: Int, val message: String) : NetworkException()
    data class UnknownError(val message: String) : NetworkException()
    data object NoInternet : NetworkException()
}