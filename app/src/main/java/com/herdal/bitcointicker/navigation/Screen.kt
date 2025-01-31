package com.herdal.bitcointicker.navigation

sealed class Screen(val route: String) {
    data object Authentication : Screen("authentication")
    data object Home : Screen("home")
    data object MyCoins : Screen("myCoins")
    data object CoinDetail : Screen("coinDetail/{id}") {
        fun createRoute(id: String) = "coinDetail/$id"
    }
}
