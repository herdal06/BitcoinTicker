package com.herdal.bitcointicker.core.data.remote

sealed class Error {
    sealed class NetworkError : Error() {
        data class NetworkUnavailable(val message: String) : NetworkError()
        data class Timeout(val message: String) : NetworkError()
        data class HttpError(val code: Int, val message: String) : NetworkError()
        data class UnknownError(val message: String) : NetworkError()
    }

    sealed class AuthError : Error() {
        data class InvalidCredentials(val message: String) : AuthError()
        data class UserNotFound(val message: String) : AuthError()
        data class AccountDisabled(val message: String) : AuthError()
        data class UnknownAuthError(val message: String) : AuthError()
    }

    sealed class FirestoreError : Error() {
        data class DocumentNotFound(val message: String) : FirestoreError()
        data class WriteError(val message: String) : FirestoreError()
        data class UnknownFirestoreError(val message: String) : FirestoreError()
    }

    sealed class RoomDatabaseError : Error() {
        data class DataNotFound(val message: String) : RoomDatabaseError()
        data class QueryError(val message: String) : RoomDatabaseError()
        data class UnknownRoomError(val message: String) : RoomDatabaseError()
    }

    data object GeneralError : Error()
}