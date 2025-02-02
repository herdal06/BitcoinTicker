package com.herdal.bitcointicker.features.coin.presentation.mycoins

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.herdal.bitcointicker.R
import com.herdal.bitcointicker.core.ui.components.ErrorDialog
import com.herdal.bitcointicker.core.ui.components.LoadingScreen
import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel

@Composable
fun MyCoinsScreen(
    viewModel: MyCoinsViewModel = hiltViewModel(),
    onClickCoin: (String?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            LoadingScreen()
        }

        state.errorMessage != null -> {
            ErrorDialog(message = state.errorMessage.orEmpty())
        }

        state.favoriteCoins.isEmpty() -> {
            EmptyFavoritesContent()
        }

        else -> {
            MyCoinsContent(
                coins = state.favoriteCoins,
                onClickCoin = onClickCoin,
                onRemoveFromFavorites = { viewModel.removeFromFavorites(it) }
            )
        }
    }
}

@Composable
private fun MyCoinsContent(
    coins: List<FavoriteCoinUiModel>,
    onClickCoin: (String) -> Unit,
    onRemoveFromFavorites: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = coins,
            key = { it.id ?: 0 }
        ) { coin ->
            FavoriteCoinItem(
                coin = coin,
                onClickCoin = { onClickCoin(coin.id ?: "") },
                onRemoveFromFavorites = { onRemoveFromFavorites(coin.id ?: "") }
            )
        }
    }
}

@Composable
fun FavoriteCoinItem(
    coin: FavoriteCoinUiModel,
    onClickCoin: (String) -> Unit,
    onRemoveFromFavorites: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickCoin(coin.id.orEmpty()) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Coin Image
            AsyncImage(
                model = coin.image,
                contentDescription = "Coin image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Coin Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coin.name.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = coin.symbol.orEmpty(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.7f
                        )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Remove Button
            IconButton(onClick = { onRemoveFromFavorites(coin.id.orEmpty()) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from favorites"
                )
            }
        }
    }
}

@Composable
private fun EmptyFavoritesContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.no_favorites),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.add_favorites_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}