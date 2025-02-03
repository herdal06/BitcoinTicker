package com.herdal.bitcointicker.features.coin.presentation.coindetail

import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel

data class CoinDetailState(
    val coinDetail: CoinDetailUiModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isCoinFavorite: Boolean = false
)