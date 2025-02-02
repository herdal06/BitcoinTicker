package com.herdal.bitcointicker.features.coin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel

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
    val currentPrice: String?,
    val fullyDilutedValuation: Long?,
    val high24h: Double?,
    val image: String?,
    val lastUpdated: String?,
    val low24h: Double?,
    val marketCap: String?,
    val marketCapChange24h: Double?,
    val marketCapChangePercentage24h: Double?,
    val marketCapRank: String?,
    val maxSupply: Double?,
    val priceChange24h: Double?,
    val priceChangePercentage24h: Double?,
    val roi: RoinEntity?,
    val totalSupply: Double?,
    val totalVolume: Long?,
    val priceChangeText: String?
)

fun CoinEntity.toDomain(): CoinUiModel = CoinUiModel(
    name = name,
    symbol = symbol,
    image = image,
    currentPrice = currentPrice,
    marketCap = marketCap,
    marketCapRank = marketCapRank,
    totalVolume = totalVolume,
    high24h = high24h,
    low24h = low24h,
    priceChange24h = priceChange24h,
    priceChangePercentage24h = priceChangePercentage24h,
    marketCapChange24h = marketCapChange24h,
    marketCapChangePercentage24h = marketCapChangePercentage24h,
    circulatingSupply = circulatingSupply,
    totalSupply = totalSupply,
    maxSupply = maxSupply,
    ath = ath,
    athChangePercentage = athChangePercentage,
    athDate = athDate,
    atl = atl,
    atlChangePercentage = atlChangePercentage,
    atlDate = atlDate,
    lastUpdated = lastUpdated,
    roi = roi?.toDomain(),
    fullyDilutedValuation = fullyDilutedValuation,
    id = coinId,
    priceChangeText = priceChangeText
)