package com.herdal.bitcointicker.features.coin.presentation.coindetail

import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel

data class CoinDetailState(
    val coin: UiState<CoinDetailUiModel> = UiState.Loading
)