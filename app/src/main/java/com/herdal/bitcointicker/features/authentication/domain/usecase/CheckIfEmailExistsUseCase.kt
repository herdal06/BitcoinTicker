package com.herdal.bitcointicker.features.authentication.domain.usecase

import com.herdal.bitcointicker.core.domain.BaseUseCase
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIfEmailExistsUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase<Boolean>() {
    fun execute(email: String): Flow<UiState<Boolean>> {
        return super.execute {
            authenticationRepository.checkIfEmailExists(email)
        }
    }
}