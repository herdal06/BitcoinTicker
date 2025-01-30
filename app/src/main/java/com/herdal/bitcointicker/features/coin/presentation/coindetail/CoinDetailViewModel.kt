package com.herdal.bitcointicker.features.coin.presentation.coindetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.bitcointicker.features.coin.domain.usecase.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getCoinDetail(id)
        }
    }

    private fun getCoinDetail(id: String) {
        viewModelScope.launch {
            getCoinDetailUseCase.execute(id)
                .collect { uiState ->
                    _state.update { it.copy(coin = uiState) }
                }
        }
    }
}