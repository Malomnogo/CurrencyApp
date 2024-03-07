package com.malomnogo

import com.malomnogo.data.ProvideResources
import com.malomnogo.data.core.HandleError
import com.malomnogo.data.dashboard.BaseDashboardRepository
import com.malomnogo.data.dashboard.CurrencyPairRatesDataSource
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.load.BaseLoadCurrenciesRepository
import com.malomnogo.data.load.cache.CurrenciesCacheDataSource
import com.malomnogo.data.load.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.data.settings.BaseSettingsRepository
import com.malomnogo.domain.dashboard.DashboardItem
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult
import com.malomnogo.domain.load.LoadCurrenciesRepository
import com.malomnogo.domain.load.LoadCurrenciesResult
import com.malomnogo.domain.settings.SettingsRepository

interface ProvideInstance {

    fun provideLoadCurrenciesRepository(
        cloudDataSource: LoadCurrenciesCloudDataSource,
        cacheDataSource: CurrenciesCacheDataSource.Mutable,
        provideResources: ProvideResources
    ): LoadCurrenciesRepository

    fun provideDashboardRepository(
        currencyPairCacheDataSource: CurrencyPairCacheDataSource.Base,
        currencyPairRatesDataSource: CurrencyPairRatesDataSource.Base,
        handleError: HandleError.Base
    ): DashboardRepository

    fun provideSettingsRepository(
        currenciesCacheDataSource: CurrenciesCacheDataSource.Base,
        currencyPairRatesDataSource: CurrencyPairCacheDataSource.Base
    ): SettingsRepository

    class Base : ProvideInstance {

        override fun provideLoadCurrenciesRepository(
            cloudDataSource: LoadCurrenciesCloudDataSource,
            cacheDataSource: CurrenciesCacheDataSource.Mutable,
            provideResources: ProvideResources
        ) = BaseLoadCurrenciesRepository(
            cacheDataSource = cacheDataSource,
            cloudDataSource = cloudDataSource,
            provideResources = provideResources
        )

        override fun provideDashboardRepository(
            currencyPairCacheDataSource: CurrencyPairCacheDataSource.Base,
            currencyPairRatesDataSource: CurrencyPairRatesDataSource.Base,
            handleError: HandleError.Base
        ) = BaseDashboardRepository(
            cacheDataSource = currencyPairCacheDataSource,
            currencyPairRatesDataSource = currencyPairRatesDataSource,
            handleError = handleError
        )

        override fun provideSettingsRepository(
            currenciesCacheDataSource: CurrenciesCacheDataSource.Base,
            currencyPairRatesDataSource: CurrencyPairCacheDataSource.Base
        ) = BaseSettingsRepository(
            allCacheDataSource = currenciesCacheDataSource,
            favoriteCacheDataSource = currencyPairRatesDataSource
        )
    }

    class Mock : ProvideInstance {

        override fun provideLoadCurrenciesRepository(
            cloudDataSource: LoadCurrenciesCloudDataSource,
            cacheDataSource: CurrenciesCacheDataSource.Mutable,
            provideResources: ProvideResources
        ): LoadCurrenciesRepository = FakeLoadCurrenciesRepository()

        override fun provideDashboardRepository(
            currencyPairCacheDataSource: CurrencyPairCacheDataSource.Base,
            currencyPairRatesDataSource: CurrencyPairRatesDataSource.Base,
            handleError: HandleError.Base
        ): DashboardRepository = FakeDashboardRepository()

        override fun provideSettingsRepository(
            currenciesCacheDataSource: CurrenciesCacheDataSource.Base,
            currencyPairRatesDataSource: CurrencyPairCacheDataSource.Base
        ): SettingsRepository = FakeSettingsRepository()

        private companion object {
            private val favoriteCurrencies = mutableListOf<Pair<String, String>>()
        }

        private class FakeLoadCurrenciesRepository : LoadCurrenciesRepository {

            private var counter = 0

            override suspend fun fetchCurrencies(): LoadCurrenciesResult {
                return if (counter++ == 0)
                    LoadCurrenciesResult.Error("No internet connection")
                else
                    LoadCurrenciesResult.Success
            }
        }

        private class FakeDashboardRepository : DashboardRepository {

            override suspend fun dashboardItems(): DashboardResult {
                return if (favoriteCurrencies.isEmpty())
                    DashboardResult.Empty
                else
                    DashboardResult.Success(
                        favoriteCurrencies.map {
                            DashboardItem.Base(
                                from = it.first,
                                to = it.second,
                                rates = 123.45
                            )
                        })
            }

            override suspend fun removePair(from: String, to: String): DashboardResult {
                favoriteCurrencies.remove(Pair(from, to))
                return dashboardItems()
            }
        }

        private class FakeSettingsRepository : SettingsRepository {

            override suspend fun currencies(): List<String> = listOf("USD", "EUR", "JPY")

            override suspend fun currenciesDestinations(from: String): List<String> {
                val allCurrencies = currencies().toMutableList().also { it.remove(from) }

                val matched = favoriteCurrencies.filter { it.first == from }.map { it.second }
                allCurrencies.removeAll(matched)
                return allCurrencies
            }

            override suspend fun save(from: String, to: String) {
                favoriteCurrencies.add(Pair(from, to))
            }
        }
    }
}