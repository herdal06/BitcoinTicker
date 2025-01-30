package com.herdal.bitcointicker.features.coin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val coinId: String?,
    val name: String?,
    val symbol: String?,
    val ath: Double?,
    val athChangePercentage: Double?,
    val athDate: String?,
    val atl: Double?,
    val atlChangePercentage: Double?,
    val atlDate: String?,
    val circulatingSupply: Double?,
    val currentPrice: Double?,
    val fullyDilutedValuation: Long?,
    val high24h: Double?,
    val image: String?,
    val lastUpdated: String?,
    val low24h: Double?,
    val marketCap: Long?,
    val marketCapChange24h: Double?,
    val marketCapChangePercentage24h: Double?,
    val marketCapRank: Int?,
    val maxSupply: Double?,
    val priceChange24h: Double?,
    val priceChangePercentage24h: Double?,
    val roi: RoinEntity?,
    val totalSupply: Double?,
    val totalVolume: Long?
)