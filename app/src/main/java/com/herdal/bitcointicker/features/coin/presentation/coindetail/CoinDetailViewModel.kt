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
            getCoinDetailUseCase.execute(id).collect { uiState ->
                _state.update {
                    when (uiState) {
                        UiState.Loading -> it.copy(isLoading = true)
                        is UiState.Success -> it.copy(
                            isLoading = false,
                            coinDetail = uiState.data,
                            isCoinFavorite = uiState.data.isFavorite
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

    fun toggleFavorite() {
        val currentCoinDetail = _state.value.coinDetail

        currentCoinDetail?.let {
            viewModelScope.launch {
                if (_state.value.isCoinFavorite) {
                    deleteCoinFromFavoritesUseCase.execute(it.id.orEmpty()).collect { uiState ->
                        _state.update {
                            when (uiState) {
                                UiState.Loading -> it.copy(isLoading = false)
                                is UiState.Success -> it.copy(
                                    isLoading = false,
                                    isCoinFavorite = false
                                )

                                is UiState.Error -> it.copy(
                                    isLoading = false,
                                    errorMessage = uiState.message
                                )
                            }
                        }
                    }
                } else {
                    addCoinToFavoritesUseCase.execute(currentCoinDetail.toFavoriteCoinUiModel())
                        .collect { uiState ->
                            _state.update {
                                when (uiState) {
                                    UiState.Loading -> it.copy(isLoading = false)
                                    is UiState.Success -> it.copy(
                                        isLoading = false,
                                        isCoinFavorite = true
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
}

fun CoinDetailUiModel.toFavoriteCoinUiModel(): FavoriteCoinUiModel {
    return FavoriteCoinUiModel(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        image = this.largeImage
    )
}