package com.malomnogo.data.latestCurrency

import com.malomnogo.data.core.HandleError
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCacheDataSource
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult

class BaseDashboardRepository(
    private val cacheDataSource: LatestCurrencyCacheDataSource.Read,
    private val currencyPairRatesDataSource: CurrencyPairRatesDataSource,
    private val handleError: HandleError
) : DashboardRepository {

    override suspend fun dashboardItems(): DashboardResult {
        val favoriteRates = cacheDataSource.read()
        return if (favoriteRates.isEmpty())
            DashboardResult.Empty
        else try {
            DashboardResult.Success(currencyPairRatesDataSource.data(favoriteRates))
        } catch (e: Exception) {
            DashboardResult.Error(handleError.handleError(e))
        }
    }
}