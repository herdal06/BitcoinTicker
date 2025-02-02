package com.herdal.bitcointicker.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.herdal.bitcointicker.R
import com.herdal.bitcointicker.core.util.logE
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoinItem(
    coin: CoinUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            coin.image?.let { logE(it) }
            AsyncImage(
                model = coin.image,
                contentDescription = "BNB Coin Image",
                modifier = Modifier.size(20.dp).weight(1f),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground), // Geçici bir yükleme resmi
                error = painterResource(id = R.drawable.ic_launcher_background),
                onError =  {
                 logE(it.result.toString())
                }
            )


            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = coin.name.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = coin.symbol.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Text(
                text = "$${coin.currentPrice}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}
