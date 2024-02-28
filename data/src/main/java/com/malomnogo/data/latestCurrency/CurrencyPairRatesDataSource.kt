package com.malomnogo.data.latestCurrency

import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCache
import com.malomnogo.domain.dashboard.DashboardItem

interface CurrencyPairRatesDataSource {

    suspend fun data(favoriteRates: List<LatestCurrencyCache>): List<DashboardItem>

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRateDataSource: UpdatedRateDataSource
    ) : CurrencyPairRatesDataSource {

        override suspend fun data(favoriteRates: List<LatestCurrencyCache>) =
            favoriteRates.map { favoriteRate ->
                DashboardItem.Base(
                    from = favoriteRate.from,
                    to = favoriteRate.to,
                    rates = if (favoriteRate.isNotFresh(currentTimeInMillis))
                        updatedRateDataSource.updatedRate(favoriteRate)
                    else
                        favoriteRate.rate
                )
            }
    }
}