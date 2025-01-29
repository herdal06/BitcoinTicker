package com.herdal.bitcointicker.features.coin.presentation.mycoins

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MyCoinsScreen(
    modifier: Modifier = Modifier,
    viewModel: MyCoinsViewModel = hiltViewModel(),
    onClickCoin: (id: String) -> Unit
) {
    Text("favorites")
}