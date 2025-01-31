package com.herdal.bitcointicker.features.coin.presentation.mycoins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel
import com.herdal.bitcointicker.features.coin.domain.usecase.AddCoinToFavoritesUseCase
import com.herdal.bitcointicker.features.coin.domain.usecase.DeleteCoinFromFavoritesUseCase
import com.herdal.bitcointicker.features.coin.domain.usecase.GetFavoriteCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCoinsViewModel @Inject constructor(
    private val getFavoriteCoinsUseCase: GetFavoriteCoinsUseCase,
    private val addToFavoritesUseCase: AddCoinToFavoritesUseCase,
    private val removeFromFavoritesUseCase: DeleteCoinFromFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyCoinsState())
    val state = _state.asStateFlow()

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            getFavoriteCoinsUseCase.execute()
                .collect { uiState ->
                    _state.update { it.copy(favoriteCoins = uiState) }
                }
        }
    }

    fun addToFavorites(coin: FavoriteCoinUiModel) {
        viewModelScope.launch {
            addToFavoritesUseCase.execute(coin)
                .collect { uiState ->
                    _state.update { state ->
                        when (uiState) {
                            is UiState.Success -> {
                                val currentList =
                                    (state.favoriteCoins as? UiState.Success)?.data.orEmpty()
                                val newCoin = FavoriteCoinUiModel(
                                    id = coin.id,
                                    name = coin.name,
                                    symbol = coin.symbol,
                                    image = coin.image,
                                    addedAt = System.currentTimeMillis()
                                )
                                state.copy(
                                    favoriteCoins = UiState.Success(currentList + newCoin),
                                    favoriteAdded = uiState
                                )
                            }

                            else -> state.copy(favoriteAdded = uiState)
                        }
                    }
                }
        }
    }

    fun removeFromFavorites(coinId: String) {
        viewModelScope.launch {
            removeFromFavoritesUseCase.execute(coinId)
                .collect { uiState ->
                    _state.update { state ->
                        when (uiState) {
                            is UiState.Success -> {
                                val currentList =
                                    (state.favoriteCoins as? UiState.Success)?.data.orEmpty()
                                val updatedList = currentList.filter { it.id != coinId }
                                state.copy(
                                    favoriteCoins = UiState.Success(updatedList),
                                    favoriteDeleted = uiState
                                )
                            }

                            else -> state.copy(favoriteDeleted = uiState)
                        }
                    }
                }
        }
    }
}