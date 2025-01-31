package com.herdal.bitcointicker.features.coin.data.firebase.model

import com.herdal.bitcointicker.features.coin.domain.uimodel.FavoriteCoinUiModel

data class FavoriteCoinFirebaseModel(
    val id: String?,
    val name: String?,
    val symbol: String?,
    val image: String?,
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