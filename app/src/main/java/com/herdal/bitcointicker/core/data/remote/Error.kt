package com.herdal.bitcointicker.core.data.remote

sealed class Error(open val message: String) {
    sealed class NetworkError(message: String) : Error(message) {
        data class NetworkUnavailable(override val message: String) : NetworkError(message)
        data class IOError(override val message: String) : NetworkError(message)
        data class HttpError(val code: Int, override val message: String) : NetworkError(message)
        data class UnknownError(override val message: String) : NetworkError(message)
    }

    sealed class AuthError(message: String) : Error(message) {
        data class InvalidCredentials(override val message: String) : AuthError(message)
        data class UserNotFound(override val message: String) : AuthError(message)
        data class AccountDisabled(override val message: String) : AuthError(message)
        data class UnknownAuthError(override val message: String) : AuthError(message)
    }

    sealed class FirestoreError(message: String) : Error(message) {
        data class DocumentNotFound(override val message: String) : FirestoreError(message)
        data class WriteError(override val message: String) : FirestoreError(message)
        data class UnknownFirestoreError(override val message: String) : FirestoreError(message)
        data class AuthError(override val message: String) : FirestoreError(message)
    }

    sealed class RoomDatabaseError(message: String) : Error(message) {
        data class DataNotFound(override val message: String) : RoomDatabaseError(message)
        data class QueryError(override val message: String) : RoomDatabaseError(message)
        data class UnknownRoomError(override val message: String) : RoomDatabaseError(message)
    }

    data object GeneralError : Error("An Error Occurred")
}