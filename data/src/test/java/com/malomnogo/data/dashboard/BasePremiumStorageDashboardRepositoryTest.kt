package com.malomnogo.data.dashboard

import com.malomnogo.data.core.FakeHandleError
import com.malomnogo.data.core.FakeProvideResources
import com.malomnogo.data.currencies.cache.CurrenciesCacheDataSource
import com.malomnogo.data.currencies.cache.CurrencyCache
import com.malomnogo.data.currencies.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
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
    private lateinit var currencyCacheDataSource: FakeCurrencyCacheDataSource
    private lateinit var loadCurrenciesCloudDataSource: FakeLoadCurrenciesCloudDataSource

    @Before
    fun setup() {
        cacheDataSource = FakeCacheDataSource()
        currencyPairRatesDataSource = FakeCurrencyPairRatesDataSource()
        currencyCacheDataSource = FakeCurrencyCacheDataSource()
        loadCurrenciesCloudDataSource = FakeLoadCurrenciesCloudDataSource()
        repository = BaseDashboardRepository(
            cacheDataSource = cacheDataSource,
            currencyPairRatesDataSource = currencyPairRatesDataSource,
            allCurrenciesCacheDataSource = currencyCacheDataSource,
            cloudDataSource = loadCurrenciesCloudDataSource,
            handleError = FakeHandleError(FakeProvideResources())
        )
    }

    @Test
    fun successCurrencies() =
        runBlocking {
            val expected = hashMapOf(
                "USD" to "United States Dollar",
                "AUD" to "Australian Dollar",
                "JPY" to "Japanese Yen"
            )

            loadCurrenciesCloudDataSource.successResult()
            currencyCacheDataSource.noHaveCache()
            repository.dashboardItems()
            currencyCacheDataSource.checkCurrencies(expected)

            loadCurrenciesCloudDataSource.expectException(UnknownHostException())
            currencyCacheDataSource.haveCache()
            repository.dashboardItems()
            currencyCacheDataSource.checkCurrencies(expected)
        }


    @Test
    fun testEmpty() = runBlocking {
        loadCurrenciesCloudDataSource.successResult()
        cacheDataSource.empty()
        val expected = repository.dashboardItems()
        assertEquals(expected, DashboardResult.Empty)
    }

    @Test
    fun testSuccess() = runBlocking {
        loadCurrenciesCloudDataSource.successResult()
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
        loadCurrenciesCloudDataSource.expectException(UnknownHostException())
        cacheDataSource.notEmpty()
        currencyPairRatesDataSource.error()
        val expected = repository.dashboardItems()
        assertEquals(expected, DashboardResult.Error("No internet connection"))
    }

    @Test
    fun testRemove() = runBlocking {
        loadCurrenciesCloudDataSource.successResult()
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

private class FakeCurrencyCacheDataSource : CurrenciesCacheDataSource.Mutable {

    private val actualCurrencies = mutableMapOf<String, String>()
    private var haveCache = false

    fun checkCurrencies(expected: Map<String, String>) {
        assertEquals(expected, actualCurrencies)
    }

    fun haveCache() {
        haveCache = true
    }

    fun noHaveCache() {
        haveCache = false
    }

    override suspend fun save(currencies: HashMap<String, String>) {
        this.actualCurrencies.putAll(currencies)
    }

    override suspend fun read() = if (haveCache)
        listOf(
            CurrencyCache("USD", "United States Dollar"),
            CurrencyCache("AUD", "Australian Dollar"),
            CurrencyCache("JPY", "Japanese Yen")
        ) else
        emptyList()
}

private class FakeLoadCurrenciesCloudDataSource : LoadCurrenciesCloudDataSource {

    private var isSuccessResult: Boolean = false

    private lateinit var exception: Exception

    fun successResult() {
        isSuccessResult = true
    }

    fun expectException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun currencies(): HashMap<String, String> {
        if (isSuccessResult)
            return hashMapOf(
                "USD" to "United States Dollar",
                "AUD" to "Australian Dollar",
                "JPY" to "Japanese Yen"
            )
        else
            throw exception
    }
}
