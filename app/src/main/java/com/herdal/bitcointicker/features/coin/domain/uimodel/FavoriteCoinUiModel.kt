package com.herdal.bitcointicker.features.coin.domain.uimodel

import com.herdal.bitcointicker.features.coin.data.firebase.model.FavoriteCoinFirebaseModel

data class FavoriteCoinUiModel(
    val id: String? = null,
    val name: String? = null,
    val symbol: String? = null,
    val image: String? = null,
    val addedAt: Long = System.currentTimeMillis()
)

fun FavoriteCoinUiModel.toFirebaseModel(): FavoriteCoinFirebaseModel =
    FavoriteCoinFirebaseModel(
        id = id,
        name = name,
        symbol = symbol,
        image = image,
        addedAt = addedAt
    )