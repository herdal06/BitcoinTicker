package com.herdal.bitcointicker.features.home.presentation

import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel

data class HomeState(
    val coins: UiState<List<CoinUiModel>> = UiState.Loading,
    val noCoinsFound: Boolean = false
)