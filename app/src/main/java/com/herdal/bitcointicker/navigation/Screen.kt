package com.herdal.bitcointicker.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Authentication : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object MyCoins : Screen()

    @Serializable
    data class CoinDetail(val id: String?) : Screen()
}