package com.herdal.bitcointicker.features.home.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.core.ui.components.ErrorDialog
import com.herdal.bitcointicker.core.ui.components.LoadingScreen
import com.herdal.bitcointicker.features.home.presentation.components.CoinItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickCoin: (id: String?) -> Unit
) {
    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    var isDialogVisible by remember { mutableStateOf(false) }

    when (val uiState = homeState.coins) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            val coins = uiState.data
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(items = coins, key = { coin -> coin.id ?: "" }) { coin ->
                    CoinItem(
                        coin = coin,
                        onClick = { onClickCoin(coin.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        is UiState.Error -> {
            ErrorDialog(message = uiState.message, onDismiss = { isDialogVisible = false })
        }
    }
}