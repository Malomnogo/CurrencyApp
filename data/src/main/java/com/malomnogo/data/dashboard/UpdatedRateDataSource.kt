package com.malomnogo.data.dashboard

import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.dashboard.cloud.CurrencyRateCloudDataSource

interface UpdatedRateDataSource {

    suspend fun updatedRate(currentPair: CurrencyPairCache): Double

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val cloudDataSource: CurrencyRateCloudDataSource,
        private val cacheDataSource: CurrencyPairCacheDataSource.Save
    ) : UpdatedRateDataSource {

        override suspend fun updatedRate(currentPair: CurrencyPairCache): Double {
            val updatedRate = cloudDataSource.latestCurrency(currentPair.from, currentPair.to)
            cacheDataSource.save(
                CurrencyPairCache(
                    currentPair.from,
                    currentPair.to,
                    updatedRate,
                    currentTimeInMillis.time()
                )
            )
            return updatedRate
        }
    }
}