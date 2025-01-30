package com.herdal.bitcointicker.features.coin.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinUiModel

data class CoinDto(
    val ath: Double?,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double?,
    @SerializedName("ath_date")
    val athDate: String?,
    val atl: Double?,
    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double?,
    @SerializedName("atl_date")
    val atlDate: String?,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double?,
    @SerializedName("current_price")
    val currentPrice: Double?,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Long?,
    @SerializedName("high_24h")
    val high24h: Double?,
    val id: String?,
    val image: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("low_24h")
    val low24h: Double?,
    @SerializedName("market_cap")
    val marketCap: Long?,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double?,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double?,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int?,
    @SerializedName("max_supply")
    val maxSupply: Double?,
    val name: String?,
    @SerializedName("price_change_24h")
    val priceChange24h: Double?,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    val roi: RoiDto?,
    val symbol: String?,
    @SerializedName("total_supply")
    val totalSupply: Double?,
    @SerializedName("total_volume")
    val totalVolume: Long?
)

fun CoinDto.toDomain(): CoinUiModel =
    CoinUiModel(
        id = id.orEmpty(),
        name = name.orEmpty(),
        symbol = symbol.orEmpty(),
        image = image.orEmpty(),
        currentPrice = currentPrice ?: 0.0,
        marketCap = marketCap ?: 0L,
        marketCapRank = marketCapRank ?: 0,
        totalVolume = totalVolume ?: 0L,
        high24h = high24h ?: 0.0,
        low24h = low24h ?: 0.0,
        priceChange24h = priceChange24h ?: 0.0,
        priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
        marketCapChange24h = marketCapChange24h ?: 0.0,
        marketCapChangePercentage24h = marketCapChangePercentage24h ?: 0.0,
        circulatingSupply = circulatingSupply ?: 0.0,
        totalSupply = totalSupply,
        maxSupply = maxSupply,
        ath = ath ?: 0.0,
        athChangePercentage = athChangePercentage ?: 0.0,
        athDate = athDate.orEmpty(),
        atl = atl ?: 0.0,
        atlChangePercentage = atlChangePercentage ?: 0.0,
        atlDate = atlDate.orEmpty(),
        lastUpdated = lastUpdated.orEmpty(),
        roi = roi?.toDomain(),
        fullyDilutedValuation = fullyDilutedValuation
    )