package com.herdal.bitcointicker.features.home.presentation

import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel

data class HomeState(
    val coins: List<CoinUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val noCoinsFound: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String? = null
)