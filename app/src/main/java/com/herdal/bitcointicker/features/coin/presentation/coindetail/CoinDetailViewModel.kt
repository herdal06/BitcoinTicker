package com.herdal.bitcointicker.features.coin.presentation.coindetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel
import com.herdal.bitcointicker.features.coin.domain.usecase.AddCoinToFavoritesUseCase
import com.herdal.bitcointicker.features.coin.domain.usecase.DeleteCoinFromFavoritesUseCase
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
    private val addCoinToFavoritesUseCase: AddCoinToFavoritesUseCase,
    private val deleteCoinFromFavoritesUseCase: DeleteCoinFromFavoritesUseCase,
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

    fun toggleFavorite() {
        val currentState = _state.value.coin
        if (currentState is UiState.Success) {
            val coin = currentState.data
            val favoriteCoin = coin.toFavoriteCoinUiModel()

            viewModelScope.launch {
                if (coin.isFavorite) {
                    favoriteCoin.id?.let { id ->
                        deleteCoinFromFavoritesUseCase.execute(id)
                            .collect { result ->
                                when (result) {
                                    is UiState.Loading -> {
                                        _state.update { it.copy(coin = UiState.Loading) }
                                    }
                                    is UiState.Success -> {
                                        _state.update {
                                            it.copy(
                                                coin = UiState.Success(
                                                    coin.copy(isFavorite = false)
                                                )
                                            )
                                        }
                                    }
                                    else -> UiState.Error("delete coin operation failed")
                                }
                            }
                    }
                } else {
                    addCoinToFavoritesUseCase.execute(favoriteCoin)
                        .collect { result ->
                            when (result) {
                                is UiState.Success -> {
                                    _state.update {
                                        it.copy(
                                            coin = UiState.Success(
                                                coin.copy(isFavorite = true)
                                            )
                                        )
                                    }
                                }
                                else -> UiState.Error("add coin operation failed")
                            }
                        }
                }
            }
        }
    }
}

fun CoinDetailUiModel.toFavoriteCoinUiModel(): FavoriteCoinUiModel {
    return FavoriteCoinUiModel(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        image = this.largeImage
    )
}