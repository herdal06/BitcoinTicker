package com.herdal.bitcointicker.features.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.authentication.domain.usecase.CheckIfEmailExistsUseCase
import com.herdal.bitcointicker.features.authentication.domain.usecase.LoginUserUseCase
import com.herdal.bitcointicker.features.authentication.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val checkIfEmailExistsUseCase: CheckIfEmailExistsUseCase
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthenticationState())
    val authState = _authState.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUserUseCase.execute(email, password).collect { uiState ->
                _authState.update {
                    when (uiState) {
                        UiState.Loading -> {
                            it.copy(isLoading = true)
                        }

                        is UiState.Success -> {
                            it.copy(isLoading = false, isUserLoggedIn = true)
                        }

                        is UiState.Error -> {
                            it.copy(isLoading = false, errorMessage = uiState.message)
                        }
                    }
                }
            }
        }
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            checkIfEmailExistsUseCase.execute(email).collect { emailCheckState ->
                when (emailCheckState) {
                    UiState.Loading -> {
                        _authState.update { it.copy(isLoading = true) }
                    }

                    is UiState.Success -> {
                        if (emailCheckState.data) {
                            _authState.update {
                                it.copy(
                                    isLoading = false,
                                    isEmailAlreadyExists = true,
                                    errorMessage = it.errorMessage
                                )
                            }
                        } else {
                            registerUserUseCase.execute(email, password).collect { registerState ->
                                _authState.update {
                                    when (registerState) {
                                        UiState.Loading -> {
                                            it.copy(isLoading = true)
                                        }

                                        is UiState.Success -> {
                                            it.copy(
                                                isLoading = false,
                                                isUserSignedUp = true
                                            )
                                        }

                                        is UiState.Error -> {
                                            it.copy(
                                                isLoading = false,
                                                errorMessage = registerState.message
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    is UiState.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = emailCheckState.message
                            )
                        }
                    }
                }
            }
        }
    }
}