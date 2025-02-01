package com.herdal.bitcointicker.features.coin.data.firebase.model

import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel

data class FavoriteCoinFirebaseModel(
    val id: String? = null,
    val name: String? = null,
    val symbol: String? = null,
    val image: String? = null,
    val addedAt: Long = System.currentTimeMillis()
)

fun FavoriteCoinFirebaseModel.toDomain(): FavoriteCoinUiModel =
    FavoriteCoinUiModel(
        id = id,
        name = name,
        symbol = symbol,
        image = image,
        addedAt = addedAt
    )