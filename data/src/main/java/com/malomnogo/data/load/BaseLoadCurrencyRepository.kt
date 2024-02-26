package com.malomnogo.data.load

import com.malomnogo.data.ProvideResources
import com.malomnogo.data.load.cache.CurrencyCacheDataSource
import com.malomnogo.data.load.cloud.LoadCurrencyCloudDataSource
import com.malomnogo.domain.LoadCurrenciesRepository
import com.malomnogo.domain.LoadCurrenciesResult
import java.net.UnknownHostException

class BaseLoadCurrencyRepository(
    private val cacheDataSource: CurrencyCacheDataSource.Mutable,
    private val cloudDataSource: LoadCurrencyCloudDataSource,
    private val provideResources: ProvideResources
) : LoadCurrenciesRepository {

    override suspend fun fetchCurrencies() = try {
        if (cacheDataSource.read().isEmpty()) {
            cacheDataSource.save(cloudDataSource.currencies())
        }
        LoadCurrenciesResult.Success
    } catch (e: Exception) {
        if (e is UnknownHostException)
            LoadCurrenciesResult.Error(provideResources.noInternetConnectionMessage())
        else
            LoadCurrenciesResult.Error(provideResources.serviceUnavailableMessage())
    }
}