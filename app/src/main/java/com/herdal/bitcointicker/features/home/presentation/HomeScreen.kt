package com.herdal.bitcointicker.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.core.ui.components.ErrorDialog
import com.herdal.bitcointicker.core.ui.components.LoadingScreen
import com.herdal.bitcointicker.core.ui.components.SearchView
import com.herdal.bitcointicker.features.home.presentation.components.CoinItem
import com.herdal.bitcointicker.features.home.presentation.components.NoCoinFoundScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickCoin: (id: String?) -> Unit
) {
    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchView(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            clearQuery = { viewModel.onSearchQueryChanged("") },
            searchHint = "Kripto Ara...",
            clearContentDescription = "Aramayı Temizle",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (val uiState = homeState.coins) {
            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Success -> {
                val coins = uiState.data
                if (homeState.noCoinsFound) {
                    NoCoinFoundScreen()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(
                            items = coins,
                            key = { index, coin ->
                                "${coin.id ?: coin.symbol ?: ""}_$index"
                            }
                        ) { _, coin ->
                            CoinItem(
                                coin = coin,
                                onClick = { onClickCoin(coin.id) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            is UiState.Error -> {
                ErrorDialog(message = uiState.message, onDismiss = {})
            }
        }
    }
}