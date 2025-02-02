package com.herdal.bitcointicker.features.coin.domain.uimodel

import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity

data class CoinUiModel(
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
    val id: String?,
    val image: String?,
    val lastUpdated: String?,
    val low24h: Double?,
    val marketCap: String?,
    val marketCapChange24h: Double?,
    val marketCapChangePercentage24h: Double?,
    val marketCapRank: String?,
    val maxSupply: Double?,
    val name: String?,
    val priceChange24h: Double?,
    val priceChangePercentage24h: Double?,
    val priceChangeText: String?,
    val roi: RoiUiModel?,
    val symbol: String?,
    val totalSupply: Double?,
    val totalVolume: Long?
)

fun CoinUiModel.toEntity(): CoinEntity {
    return CoinEntity(
        coinId = id,
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
        roi = roi?.toEntity(),
        fullyDilutedValuation = fullyDilutedValuation,
        priceChangeText = priceChangeText
    )
}