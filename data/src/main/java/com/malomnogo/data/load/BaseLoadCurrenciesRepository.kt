package com.malomnogo.data.load

import com.malomnogo.data.core.HandleError
import com.malomnogo.data.load.cache.CurrenciesCacheDataSource
import com.malomnogo.data.load.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.domain.load.LoadCurrenciesRepository
import com.malomnogo.domain.load.LoadCurrenciesResult
import java.net.UnknownHostException
import javax.inject.Inject

class BaseLoadCurrenciesRepository @Inject constructor(
    private val cacheDataSource: CurrenciesCacheDataSource.Mutable,
    private val cloudDataSource: LoadCurrenciesCloudDataSource,
    private val handleError: HandleError
) : LoadCurrenciesRepository {

    override suspend fun fetchCurrencies() = try {
        if (cacheDataSource.read().isEmpty())
            cacheDataSource.save(cloudDataSource.currencies())
        LoadCurrenciesResult.Success
    } catch (e: Exception) {
        if (e is UnknownHostException)
            LoadCurrenciesResult.Error(handleError.handleError(e))
        else
            LoadCurrenciesResult.Error(handleError.handleError(e))
    }
}