package com.herdal.bitcointicker.features.coin.presentation.coindetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.core.ui.components.ErrorDialog
import com.herdal.bitcointicker.core.ui.components.LoadingScreen
import com.herdal.bitcointicker.features.coin.presentation.coindetail.components.CoinDetailContent

@Composable
fun CoinDetailScreen(
    id: String?,
    modifier: Modifier = Modifier,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val coinState = state.coin) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            CoinDetailContent(
                coin = coinState.data,
                modifier = modifier
            )
        }

        is UiState.Error -> {
            ErrorDialog(message = coinState.message, onDismiss = { })
        }
    }
}