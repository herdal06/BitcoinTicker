package com.herdal.bitcointicker.core.data.remote

sealed class IResult<out T> {
    data class Success<T>(val data: T) : IResult<T>()
    data class Failure(val error: Error) : IResult<Nothing>()
}