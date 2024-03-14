package com.malomnogo.data.dashboard

import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.domain.dashboard.DashboardItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CurrencyPairRatesDataSource {

    suspend fun data(favoriteRates: List<CurrencyPairCache>): List<DashboardItem>

    fun needUpdate(favoriteRates: List<CurrencyPairCache>): Boolean

    class Base @Inject constructor(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRateDataSource: UpdatedRateDataSource,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : CurrencyPairRatesDataSource {

        override fun needUpdate(favoriteRates: List<CurrencyPairCache>) =
            favoriteRates.find { favoriteRate ->
                favoriteRate.isNotFresh(currentTimeInMillis)
            } != null

        override suspend fun data(favoriteRates: List<CurrencyPairCache>) =
            withContext(dispatcher) {
                val results = favoriteRates.map { favoriteRate ->
                    async {
                        DashboardItem.Base(
                            from = favoriteRate.from,
                            to = favoriteRate.to,
                            rates = if (favoriteRate.isNotFresh(currentTimeInMillis))
                                updatedRateDataSource.updatedRate(favoriteRate)
                            else
                                favoriteRate.rate
                        )
                    }
                }
                results.awaitAll()
            }
    }
}