package com.herdal.bitcointicker.features.authentication.domain.usecase

import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase<Unit>() {
    fun execute(): Flow<UiState<Unit>> {
        return super.execute {
            authenticationRepository.signOut()
        }
    }
}