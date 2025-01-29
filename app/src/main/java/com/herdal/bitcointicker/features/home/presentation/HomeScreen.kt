package com.herdal.bitcointicker.features.home.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.core.ui.components.ErrorDialog
import com.herdal.bitcointicker.core.ui.components.LoadingScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickCoin: (id: String) -> Unit
) {
    val homeState by viewModel.homeState.collectAsStateWithLifecycle()

    when (val uiState = homeState.coins) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            val coins = uiState.data
            LazyColumn {
                items(coins) { coin ->
                    Text(text = coin.name.orEmpty())
                }
            }
        }

        is UiState.Error -> {
            ErrorDialog(message = uiState.message, onDismiss = {

            })
            val errorMessage = uiState.message
            Text(text = "Error: $errorMessage")
        }
    }
}