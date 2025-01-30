package com.herdal.bitcointicker.features.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                _authState.update { currentState -> currentState.copy(loginState = uiState) }
            }
        }
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            registerUserUseCase.execute(email, password).collect { uiState ->
                _authState.update { currentState -> currentState.copy(registerState = uiState) }
            }
        }
    }

    fun checkIfEmailExists(email: String) {
        viewModelScope.launch {
            checkIfEmailExistsUseCase.execute(email).collect { uiState ->
                _authState.update { currentState ->
                    currentState.copy(emailExistsState = uiState)
                }
            }
        }
    }
}