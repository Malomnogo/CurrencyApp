package com.malomnogo.data.latestCurrency

import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCache
import com.malomnogo.domain.dashboard.DashboardItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class CurrencyPairRatesDataSourceTest {

    private lateinit var currencyPairRatesDataSource: CurrencyPairRatesDataSource
    private lateinit var updatedRate: FakeUpdatedRateDataSource

    @Before
    fun setup() {
        updatedRate = FakeUpdatedRateDataSource()
        currencyPairRatesDataSource = CurrencyPairRatesDataSource.Base(
            currentTimeInMillis = FakeCurrentTimeInMillis(),
            updatedRateDataSource = updatedRate
        )
    }

    @Test
    fun testCloud() = runBlocking {
        val expected = listOf<DashboardItem>(
            DashboardItem.Base(from = "A", to = "B", rates = 1.0),
            DashboardItem.Base(from = "B", to = "C", rates = 2.0)
        )
        val actual = currencyPairRatesDataSource.data(
            listOf(
                LatestCurrencyCache(from = "A", to = "B", 1.0, 0L),
                LatestCurrencyCache(from = "B", to = "C", 2.0, 0L),
            )
        )
        assertEquals(expected, actual)
        updatedRate.checkCalled()
    }

    @Test
    fun testCache() = runBlocking {
        val expected = listOf<DashboardItem>(
            DashboardItem.Base(from = "A", to = "B", rates = 1.0),
            DashboardItem.Base(from = "B", to = "C", rates = 2.0)
        )
        val actual = currencyPairRatesDataSource.data(
            listOf(
                LatestCurrencyCache(from = "A", to = "B", 1.0, TimeUnit.HOURS.toMillis(24)),
                LatestCurrencyCache(from = "B", to = "C", 2.0, TimeUnit.HOURS.toMillis(24)),
            )
        )
        assertEquals(expected, actual)
        updatedRate.checkNotCalled()
    }
}

private class FakeUpdatedRateDataSource : UpdatedRateDataSource {

    private var isCalled = false

    fun checkCalled() {
        assertEquals(true, isCalled)
    }

    fun checkNotCalled() {
        assertEquals(false, isCalled)
    }

    override suspend fun updatedRate(currentPair: LatestCurrencyCache): Double {
        isCalled = true
        return currentPair.rate
    }
}