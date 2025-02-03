package com.herdal.bitcointicker.features.coin.domain.uimodel

data class CoinDetailUiModel(
    val blockTimeInMinutes: Int? = null,
    val categories: List<String>? = null,
    val countryOrigin: String? = null,
    val descriptionInEn: String? = null,
    val genesisDate: String? = null,
    val hashingAlgorithm: String? = null,
    val id: String? = null,
    val largeImage: String? = null,
    val lastUpdated: String? = null,
    val firsHomePageLink: String? = null,
    val marketCapRank: Int? = null,
    val currentPriceInUsd: Double? = null,
    val name: String? = null,
    val previewListing: Boolean? = null,
    val sentimentVotesDownPercentage: Double? = null,
    val sentimentVotesUpPercentage: Float = 0f,
    val symbol: String? = null,
    val watchlistPortfolioUsers: Int? = null,
    val webSlug: String? = null,
    val isFavorite: Boolean = false,
    val sentimentVotesDownPercentageFormatted: Double? = null,
    val sentimentVotesUpPercentageFormatted: Double? = null,
    val currentPriceFormatted: String = "",
    val marketCapRankFormatted: String = ""
)