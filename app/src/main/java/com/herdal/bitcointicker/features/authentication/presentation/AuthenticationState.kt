package com.herdal.bitcointicker.features.authentication.presentation

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.domain.UiState

data class AuthenticationState(
    val registerState: UiState<FirebaseUser?> = UiState.Loading,
    val loginState: UiState<FirebaseUser?> = UiState.Loading,
    val emailExistsState: UiState<Boolean> = UiState.Loading
)