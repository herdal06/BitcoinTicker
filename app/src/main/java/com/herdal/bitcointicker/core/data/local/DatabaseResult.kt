package com.herdal.bitcointicker.core.data.local

sealed class DatabaseResult<out T> {
    data class Success<out T>(val data: T) : DatabaseResult<T>()
    data class Error(val exception: DatabaseException) : DatabaseResult<Nothing>()
}