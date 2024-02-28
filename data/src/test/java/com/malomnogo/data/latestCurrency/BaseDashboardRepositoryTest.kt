package com.malomnogo.data.latestCurrency

import com.malomnogo.data.core.FakeHandleError
import com.malomnogo.data.core.FakeProvideResources
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCache
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCacheDataSource
import com.malomnogo.domain.dashboard.DashboardItem
import com.malomnogo.domain.dashboard.DashboardResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseDashboardRepositoryTest {

    private lateinit var repository: BaseDashboardRepository
    private lateinit var cacheDataSource: FakeCacheDataSource
    private lateinit var currencyPairRatesDataSource: FakeCurrencyPairRatesDataSource

    @Before
    fun setup() {
        cacheDataSource = FakeCacheDataSource()
        currencyPairRatesDataSource = FakeCurrencyPairRatesDataSource()
        repository = BaseDashboardRepository(
            cacheDataSource = cacheDataSource,
            currencyPairRatesDataSource = currencyPairRatesDataSource,
            handleError = FakeHandleError(FakeProvideResources())
        )
    }

    @Test
    fun testEmpty() = runBlocking {
        cacheDataSource.empty()
        val expected = repository.dashboardItems()
        assertEquals(expected, DashboardResult.Empty)
    }

    @Test
    fun testSuccess() = runBlocking {
        cacheDataSource.notEmpty()
        currencyPairRatesDataSource.success()
        val expected = repository.dashboardItems()
        assertEquals(
            expected, DashboardResult.Success(
                listOf(
                    DashboardItem.Base("A", "B", 1.0),
                    DashboardItem.Base("C", "D", 2.0)
                )
            )
        )
    }

    @Test
    fun testError() = runBlocking {
        cacheDataSource.notEmpty()
        currencyPairRatesDataSource.error()
        val expected = repository.dashboardItems()
        assertEquals(expected, DashboardResult.Error("No internet connection"))
    }
}

private class FakeCacheDataSource : LatestCurrencyCacheDataSource.Read {

    private lateinit var data: List<LatestCurrencyCache>

    override suspend fun read() = data

    fun empty() {
        data = listOf()
    }

    fun notEmpty() {
        data = listOf(
            LatestCurrencyCache("A", "B", 1.0, 0L),
            LatestCurrencyCache("C", "D", 2.0, 0L)
        )
    }
}

private class FakeCurrencyPairRatesDataSource : CurrencyPairRatesDataSource {

    private var isSuccess = true

    fun success() {
        isSuccess = true
    }

    fun error() {
        isSuccess = false
    }

    override suspend fun data(favoriteRates: List<LatestCurrencyCache>) =
        if (isSuccess)
            favoriteRates.map {
                DashboardItem.Base(
                    it.from,
                    it.to,
                    it.rate
                )
            } else throw UnknownHostException()
}
