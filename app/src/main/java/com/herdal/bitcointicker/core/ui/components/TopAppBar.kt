package com.herdal.bitcointicker.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onSignOut: () -> Unit
) {
    TopAppBar(
        title = { Text("Bitcoin Ticker") },
        actions = {
            IconButton(onClick = onSignOut) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out")
            }
        }
    )
}