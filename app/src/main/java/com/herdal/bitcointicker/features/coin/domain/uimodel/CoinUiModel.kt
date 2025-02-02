package com.herdal.bitcointicker.features.coin.domain.uimodel

import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity

data class CoinUiModel(
    val ath: Double? = null,
    val athChangePercentage: Double? = null,
    val athDate: String? = null,
    val atl: Double? = null,
    val atlChangePercentage: Double? = null,
    val atlDate: String? = null,
    val circulatingSupply: Double? = null,
    val currentPrice: String? = null,
    val fullyDilutedValuation: Long? = null,
    val high24h: Double? = null,
    val id: String? = null,
    val image: String? = null,
    val lastUpdated: String? = null,
    val low24h: Double? = null,
    val marketCap: String? = null,
    val marketCapChange24h: Double? = null,
    val marketCapChangePercentage24h: Double? = null,
    val marketCapRank: String? = null,
    val maxSupply: Double? = null,
    val name: String? = null,
    val priceChange24h: Double? = null,
    val priceChangePercentage24h: Double? = null,
    val priceChangeText: String? = null,
    val roi: RoiUiModel? = null,
    val symbol: String? = null,
    val totalSupply: Double? = null,
    val totalVolume: Long? = null
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