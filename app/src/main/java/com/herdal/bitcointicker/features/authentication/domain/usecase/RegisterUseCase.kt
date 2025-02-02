package com.herdal.bitcointicker.features.authentication.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase<FirebaseUser?>() {
    fun execute(email: String, password: String): Flow<UiState<FirebaseUser?>> {
        return super.execute {
            authenticationRepository.registerUser(email, password)
        }
    }
}