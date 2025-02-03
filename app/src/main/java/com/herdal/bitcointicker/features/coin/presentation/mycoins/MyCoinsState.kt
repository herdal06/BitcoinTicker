package com.herdal.bitcointicker.features.coin.presentation.mycoins

import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel

data class MyCoinsState(
    val favoriteCoins: List<FavoriteCoinUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val favoriteDeleted: Boolean = false
)