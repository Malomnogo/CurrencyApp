package com.malomnogo.data.settings

import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCache
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCacheDataSource
import com.malomnogo.data.load.cache.CurrencyCacheDataSource
import com.malomnogo.domain.settings.SettingsRepository

class BaseSettingsRepository(
    private val allCacheDataSource: CurrencyCacheDataSource.Read,
    private val favoriteCacheDataSource: LatestCurrencyCacheDataSource.Mutable
) : SettingsRepository {

    override suspend fun currencies() = allCacheDataSource.read().map { it.name }

    override suspend fun currenciesDestinations(from: String): List<String> {
        val allCurrencies = allCacheDataSource.read().map { it.name }.toMutableList()
        val favorites = favoriteCacheDataSource.read().filter { it.from == from }.map { it.to }
        allCurrencies.remove(from)
        allCurrencies.removeAll(favorites)
        return allCurrencies
    }

    override suspend fun save(from: String, to: String) {
        favoriteCacheDataSource.save(
            LatestCurrencyCache(from = from, to = to)
        )
    }
}