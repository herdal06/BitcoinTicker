package com.herdal.bitcointicker.features.authentication.presentation

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.domain.UiState

data class AuthenticationState(
    val registerState: UiState<FirebaseUser?> = UiState.Error("asd"),
    val loginState: UiState<FirebaseUser?> = UiState.Error("asd"),
    val emailExistsState: UiState<Boolean> = UiState.Error("asd")
)