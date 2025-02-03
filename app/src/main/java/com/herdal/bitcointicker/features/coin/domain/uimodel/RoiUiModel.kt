package com.herdal.bitcointicker.features.coin.domain.uimodel

import com.herdal.bitcointicker.features.coin.data.local.entity.RoinEntity

data class RoiUiModel(
    val currency: String?,
    val percentage: Double?,
    val times: Double?
)

fun RoiUiModel.toEntity(): RoinEntity =
    RoinEntity(
        currency = currency.orEmpty(),
        percentage = percentage ?: 0.0,
        times = times ?: 0.0
    )