package com.malomnogo.data.dashboard

import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.dashboard.cloud.CurrencyRateCloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdatedRateDataSourceTest {

    private lateinit var updateRate: UpdatedRateDataSource.Base
    private lateinit var cloudDataSource: FakeCurrencyRateCloudDataSource
    private lateinit var cacheDataSource: FakeLatestCurrencyCacheDataSource

    @Before
    fun setup() {
        cloudDataSource = FakeCurrencyRateCloudDataSource()
        cacheDataSource = FakeLatestCurrencyCacheDataSource()
        updateRate = UpdatedRateDataSource.Base(
            currentTimeInMillis = FakeCurrentTimeInMillis(1234L),
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource
        )
    }

    @Test
    fun test() = runBlocking {
        val actual = updateRate.updatedRate(
            CurrencyPairCache(
                from = "A",
                to = "B",
                rate = -1.0,
                lastUpdate = 0L
            )
        )
        val expected = 123.0
        assertEquals(expected, actual, 0.001)
        cloudDataSource.check("A", "B")
        cacheDataSource.check(
            CurrencyPairCache(
                from = "A",
                to = "B",
                rate = 123.0,
                1234L
            )
        )
    }
}

private class FakeCurrencyRateCloudDataSource : CurrencyRateCloudDataSource {

    private lateinit var result: String

    override fun latestCurrency(from: String, to: String): Double {
        result = "$from/$to"
        return 123.0
    }

    fun check(from: String, to: String) {
        assertEquals("$from/$to", result)
    }
}

private class FakeLatestCurrencyCacheDataSource : CurrencyPairCacheDataSource.Save {

    private lateinit var actual: CurrencyPairCache

    override suspend fun save(currency: CurrencyPairCache) {
        actual = currency
    }

    fun check(expected: CurrencyPairCache) {
        assertEquals(expected, actual)
    }
}