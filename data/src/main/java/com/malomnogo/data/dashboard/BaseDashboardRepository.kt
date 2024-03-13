package com.malomnogo.data.dashboard

import com.malomnogo.data.core.HandleError
import com.malomnogo.data.currencies.cache.CurrenciesCacheDataSource
import com.malomnogo.data.currencies.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult
import javax.inject.Inject

class BaseDashboardRepository @Inject constructor(
    private val cacheDataSource: CurrencyPairCacheDataSource.Mutable,
    private val currencyPairRatesDataSource: CurrencyPairRatesDataSource,
    private val allCurrenciesCacheDataSource: CurrenciesCacheDataSource.Mutable,
    private val cloudDataSource: LoadCurrenciesCloudDataSource,
    private val handleError: HandleError
) : DashboardRepository {

    override suspend fun dashboardItems() = try {
        if (allCurrenciesCacheDataSource.read().isEmpty())
            allCurrenciesCacheDataSource.save(cloudDataSource.currencies())
        val favoriteRates = cacheDataSource.read()
        if (favoriteRates.isEmpty())
            DashboardResult.Empty
        else
            DashboardResult.Success(currencyPairRatesDataSource.data(favoriteRates))
    } catch (e: Exception) {
        DashboardResult.Error(handleError.handleError(e))
    }

    override suspend fun removePair(from: String, to: String): DashboardResult {
        cacheDataSource.remove(from, to)
        return dashboardItems()
    }
}