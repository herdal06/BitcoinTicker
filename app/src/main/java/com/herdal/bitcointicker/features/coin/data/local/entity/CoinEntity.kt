package com.herdal.bitcointicker.features.coin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)