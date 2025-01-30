package com.herdal.bitcointicker.features.coin.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MarketDataDto(
    @SerializedName("current_price")
    val currentPrice: CurrentPriceDto?,
)