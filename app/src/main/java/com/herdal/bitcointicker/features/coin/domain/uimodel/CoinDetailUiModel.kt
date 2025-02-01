package com.herdal.bitcointicker.features.coin.domain.uimodel

data class CoinDetailUiModel(
    val blockTimeInMinutes: Int?,
    val categories: List<String>?,
    val countryOrigin: String?,
    val descriptionInEn: String?,
    val genesisDate: String?,
    val hashingAlgorithm: String?,
    val id: String?,
    val largeImage: String?,
    val lastUpdated: String?,
    val firsHomePageLink: String?,
    val marketCapRank: Int?,
    val currentPriceInUsd: Double?,
    val name: String?,
    val previewListing: Boolean?,
    val sentimentVotesDownPercentage: Double?,
    val sentimentVotesUpPercentage: Float,
    val symbol: String?,
    val watchlistPortfolioUsers: Int?,
    val webSlug: String?,
    val isFavorite: Boolean = false
)