package com.malomnogo.data.dashboard

import com.malomnogo.data.core.FakeHandleError
import com.malomnogo.data.core.FakeProvideResources
import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.domain.dashboard.DashboardItem
import com.malomnogo.domain.dashboard.DashboardResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BasePremiumStorageDashboardRepositoryTest {

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

    @Test
    fun testRemove() = runBlocking {
        cacheDataSource.notEmpty()
        val expected = repository.removePair("A", "B")
        assertEquals(
            expected, DashboardResult.Success(listOf(DashboardItem.Base("C", "D", 2.0)))
        )
    }
}

private class FakeCacheDataSource : CurrencyPairCacheDataSource.Mutable {

    private lateinit var data: MutableList<CurrencyPairCache>

    //not used in test
    override suspend fun save(currency: CurrencyPairCache) = Unit

    override suspend fun remove(from: String, to: String) {
        data.remove(
            CurrencyPairCache(from, to, 1.0, 0L),
        )
    }

    override suspend fun read() = data

    fun empty() {
        data = mutableListOf()
    }

    fun notEmpty() {
        data = mutableListOf(
            CurrencyPairCache("A", "B", 1.0, 0L),
            CurrencyPairCache("C", "D", 2.0, 0L)
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

    override suspend fun data(favoriteRates: List<CurrencyPairCache>) =
        if (isSuccess)
            favoriteRates.map {
                DashboardItem.Base(it.from, it.to, it.rate)
            } else
              throw UnknownHostException()
}
