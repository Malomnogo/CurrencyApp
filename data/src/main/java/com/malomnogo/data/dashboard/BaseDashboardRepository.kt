package com.malomnogo.data.dashboard

import com.malomnogo.data.core.HandleError
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult

class BaseDashboardRepository(
    private val cacheDataSource: CurrencyPairCacheDataSource.Mutable,
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

    override suspend fun removePair(from: String, to: String): DashboardResult {
        cacheDataSource.remove(from, to)
        return dashboardItems()
    }
}