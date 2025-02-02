package com.herdal.bitcointicker.features.authentication.presentation

data class AuthenticationState(
    val isUserLoggedIn: Boolean = false,
    val isUserSignedUp: Boolean = false,
    val isEmailAlreadyExists: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)