package com.herdal.bitcointicker.features.coin.presentation.coindetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.herdal.bitcointicker.core.ui.components.ErrorDialog
import com.herdal.bitcointicker.core.ui.components.LoadingScreen
import com.herdal.bitcointicker.features.coin.presentation.coindetail.components.CoinDetailContent

@Composable
fun CoinDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        LoadingScreen()
    }

    if (state.errorMessage != null) {
        ErrorDialog(message = state.errorMessage.orEmpty())
    }

    state.coinDetail?.let { coinDetail ->
        CoinDetailContent(
            coin = coinDetail,
            modifier = modifier,
            isFavorite = state.isCoinFavorite,
            onFavoriteClick = { viewModel.toggleFavorite() }
        )
    }
}