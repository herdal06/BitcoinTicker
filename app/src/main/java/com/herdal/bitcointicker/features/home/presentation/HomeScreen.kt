package com.herdal.bitcointicker.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.herdal.bitcointicker.R
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

    LaunchedEffect(key1 = Unit) {
        viewModel.getCoins()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchView(
            query = homeState.searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            clearQuery = { viewModel.onSearchQueryChanged("") },
            searchHint = stringResource(R.string.search_hint),
            clearContentDescription = stringResource(R.string.clear_content_description),
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.height(8.dp))

        when {
            homeState.isLoading -> {
                LoadingScreen()
            }

            homeState.errorMessage != null -> {
                ErrorDialog(message = homeState.errorMessage.orEmpty())
            }

            homeState.noCoinsFound -> {
                NoCoinFoundScreen()
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = homeState.coins,
                        key = { index, coin -> "${coin.id ?: coin.symbol ?: ""}_$index" }
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
    }
}