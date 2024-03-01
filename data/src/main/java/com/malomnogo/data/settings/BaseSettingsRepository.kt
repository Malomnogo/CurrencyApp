package com.malomnogo.data.settings

import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.load.cache.CurrencyCacheDataSource
import com.malomnogo.domain.settings.SettingsRepository

class BaseSettingsRepository(
    private val allCacheDataSource: CurrencyCacheDataSource.Read,
    private val favoriteCacheDataSource: CurrencyPairCacheDataSource.Mutable
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
            CurrencyPairCache(from = from, to = to)
        )
    }
}