package com.herdal.bitcointicker.features.coin.presentation.coindetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel

@Composable
fun CoinDetailContent(
    coin: CoinDetailUiModel,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    var isCoinFavorite by remember { mutableStateOf(isFavorite) }

    LaunchedEffect(isFavorite) {
        isCoinFavorite = isFavorite
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        CoinHeader(
            name = coin.name.orEmpty(),
            symbol = coin.symbol.orEmpty(),
            imageUrl = coin.largeImage,
            isCoinFavorite = isCoinFavorite,
            onFavoriteClick = onFavoriteClick
        )

        CoinPriceInfo(
            price = coin.currentPriceInUsd,
            marketCapRank = coin.marketCapRank,
            lastUpdated = coin.lastUpdated.orEmpty()
        )

        Spacer(modifier = Modifier.height(24.dp))

        coin.sentimentVotesDownPercentage?.toFloat()?.let {
            CoinSentimentAnalysis(
                upPercentage = coin.sentimentVotesUpPercentage,
                downPercentage = it
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        CoinTechnicalDetails(
            genesisDate = coin.genesisDate.orEmpty(),
            hashingAlgorithm = coin.hashingAlgorithm.orEmpty(),
            blockTime = coin.blockTimeInMinutes
        )

        Spacer(modifier = Modifier.height(24.dp))

        coin.descriptionInEn?.let { description ->
            CoinDescription(description = description)
        }
    }
}

@Composable
private fun CoinHeader(
    name: String,
    symbol: String,
    imageUrl: String?,
    isCoinFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = symbol.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onFavoriteClick,
        ) {
            Icon(
                imageVector = if (isCoinFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isCoinFavorite) Color.Red else Color.Gray
            )
        }
    }
}

@Composable
private fun CoinPriceInfo(
    price: Double?,
    marketCapRank: Int?,
    lastUpdated: String,
    modifier: Modifier = Modifier
) {
    InfoCard(modifier = modifier) {
        DetailRow(
            title = "Price",
            value = "$$price"
        )
        DetailRow(
            title = "Market Cap Rank",
            value = "#$marketCapRank"
        )
        DetailRow(
            title = "Last Updated",
            value = lastUpdated
        )
    }
}

@Composable
private fun CoinSentimentAnalysis(
    upPercentage: Float,
    downPercentage: Float,
    modifier: Modifier = Modifier
) {
    InfoCard(modifier = modifier) {
        Text(
            text = "Sentiment Analysis",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LinearProgressIndicator(
            progress = { upPercentage },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(MaterialTheme.shapes.small),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.error
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Up: $upPercentage%",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Down: $downPercentage%",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun CoinTechnicalDetails(
    genesisDate: String,
    hashingAlgorithm: String,
    blockTime: Int?,
    modifier: Modifier = Modifier
) {
    InfoCard(modifier = modifier) {
        Text(
            text = "Technical Details",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DetailRow(
            title = "Genesis Date",
            value = genesisDate
        )
        DetailRow(
            title = "Hashing Algorithm",
            value = hashingAlgorithm
        )
        DetailRow(
            title = "Block Time",
            value = "$blockTime minutes"
        )
    }
}

@Composable
private fun CoinDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    InfoCard(modifier = modifier) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun InfoCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
private fun DetailRow(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}