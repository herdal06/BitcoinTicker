package com.herdal.bitcointicker.features.coin.data.remote.dto

import com.herdal.bitcointicker.features.coin.domain.uimodel.RoiUiModel

data class RoiDto(
    val currency: String?,
    val percentage: Double?,
    val times: Double?
)

fun RoiDto.toDomain(): RoiUiModel =
    RoiUiModel(
        currency = currency.orEmpty(),
        percentage = percentage ?: 0.0,
        times = times ?: 0.0
    )