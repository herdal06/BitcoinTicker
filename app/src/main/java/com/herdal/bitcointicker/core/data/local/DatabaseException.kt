package com.herdal.bitcointicker.core.data.local

sealed class DatabaseException(val message: String?) {
    class NoDataFound : DatabaseException("No data found")
    class UnknownError(message: String?) : DatabaseException(message)
}