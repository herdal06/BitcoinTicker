package com.herdal.bitcointicker.features.coin.presentation.mycoins

import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel

data class MyCoinsState(
    val favoriteCoins: UiState<List<FavoriteCoinUiModel>> = UiState.Loading,
    val favoriteAdded: UiState<Unit> = UiState.Loading,
    val favoriteDeleted: UiState<Unit> = UiState.Loading
)