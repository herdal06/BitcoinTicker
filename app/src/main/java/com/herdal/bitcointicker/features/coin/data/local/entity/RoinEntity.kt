package com.herdal.bitcointicker.features.coin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.bitcointicker.features.coin.domain.uimodel.RoiUiModel

@Entity(tableName = "roins")
data class RoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val currency: String?,
    val percentage: Double?,
    val times: Double?
)

fun RoinEntity.toDomain(): RoiUiModel =
    RoiUiModel(
        currency = currency,
        percentage = percentage,
        times = times
    )