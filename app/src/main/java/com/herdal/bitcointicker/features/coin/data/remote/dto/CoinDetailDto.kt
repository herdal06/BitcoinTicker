package com.herdal.bitcointicker.features.coin.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.herdal.bitcointicker.core.util.extensions.parseHtml
import com.herdal.bitcointicker.features.coin.domain.uimodel.CoinDetailUiModel

data class CoinDetailDto(
    @SerializedName("block_time_in_minutes")
    val blockTimeInMinutes: Int?,
    val categories: List<String>?,
    @SerializedName("country_origin")
    val countryOrigin: String?,
    val description: DescriptionDto?,
    @SerializedName("genesis_date")
    val genesisDate: String?,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String?,
    val id: String?,
    val image: CoinImageDto?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    val links: LinksDto?,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int?,
    @SerializedName("market_data")
    val marketData: MarketDataDto?,
    val name: String?,
    @SerializedName("preview_listing")
    val previewListing: Boolean?,
    @SerializedName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double?,
    @SerializedName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double?,
    val symbol: String?,
    @SerializedName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Int?,
    @SerializedName("web_slug")
    val webSlug: String?
)

fun CoinDetailDto.toDomain(): CoinDetailUiModel = CoinDetailUiModel(
    blockTimeInMinutes = blockTimeInMinutes,
    categories = categories,
    countryOrigin = countryOrigin,
    descriptionInEn = description?.en?.parseHtml(),
    genesisDate = genesisDate,
    hashingAlgorithm = hashingAlgorithm,
    id = id,
    largeImage = image?.large,
    lastUpdated = lastUpdated,
    firsHomePageLink = links?.homepage?.first(),
    marketCapRank = marketCapRank,
    currentPriceInUsd = marketData?.currentPrice?.usd,
    name = name,
    previewListing = previewListing,
    sentimentVotesDownPercentage = sentimentVotesDownPercentage ?: 0.0,
    sentimentVotesUpPercentage = ((sentimentVotesUpPercentage ?: 0.0) / 100).toFloat(),
    symbol = symbol,
    watchlistPortfolioUsers = watchlistPortfolioUsers,
    webSlug = webSlug,
    sentimentVotesDownPercentageFormatted = (sentimentVotesDownPercentage ?: 0.0) * 100,
    sentimentVotesUpPercentageFormatted = (sentimentVotesUpPercentage ?: 0.0) * 100,
    currentPriceFormatted = "%.2f".format(marketData?.currentPrice?.usd ?: 0.0),
    marketCapRankFormatted = "#${marketCapRank ?: "N/A"}"
)
