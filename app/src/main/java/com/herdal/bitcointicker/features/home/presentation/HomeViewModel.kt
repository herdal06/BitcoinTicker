package com.herdal.bitcointicker.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.usecase.GetCoinsUseCase
import com.herdal.bitcointicker.features.coin.domain.usecase.SearchCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val searchCoinsUseCase: SearchCoinsUseCase
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        getCoins()
    }

    fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase.execute().collect { uiState ->
                _homeState.update {
                    when (uiState) {
                        UiState.Loading -> it.copy(isLoading = true)
                        is UiState.Success -> it.copy(
                            isLoading = false,
                            coins = uiState.data,
                            noCoinsFound = uiState.data.isEmpty()
                        )
                        is UiState.Error -> it.copy(
                            isLoading = false,
                            errorMessage = uiState.message
                        )
                    }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _homeState.update { it.copy(searchQuery = query) }

        if (query.isEmpty()) {
            getCoins()
        } else {
            viewModelScope.launch {
                searchCoinsUseCase.execute(query).collectLatest { uiState ->
                    _homeState.update {
                        when (uiState) {
                            UiState.Loading -> it.copy(isLoading = true)
                            is UiState.Success -> it.copy(
                                isLoading = false,
                                coins = uiState.data,
                                noCoinsFound = uiState.data.isEmpty()
                            )
                            is UiState.Error -> it.copy(
                                isLoading = false,
                                errorMessage = uiState.message
                            )
                        }
                    }
                }
            }
        }
    }
}