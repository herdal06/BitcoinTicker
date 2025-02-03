package com.herdal.bitcointicker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val title: String
) {
    data object Home : BottomNavItem(
        screen = Screen.Home,
        icon = Icons.Default.Home,
        title = "Home"
    )

    data object MyCoins : BottomNavItem(
        screen = Screen.MyCoins,
        icon = Icons.Default.Favorite,
        title = "My Coins"
    )
}
