package com.malomnogo.data.load

import com.malomnogo.data.ProvideResources
import com.malomnogo.data.load.cache.CurrenciesCacheDataSource
import com.malomnogo.data.load.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.domain.load.LoadCurrenciesRepository
import com.malomnogo.domain.load.LoadCurrenciesResult
import java.net.UnknownHostException

class BaseLoadCurrenciesRepository(
    private val cacheDataSource: CurrenciesCacheDataSource.Mutable,
    private val cloudDataSource: LoadCurrenciesCloudDataSource,
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