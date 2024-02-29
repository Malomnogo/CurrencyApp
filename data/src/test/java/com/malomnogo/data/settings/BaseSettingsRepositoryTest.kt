package com.malomnogo.data.settings

import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCache
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCacheDataSource
import com.malomnogo.data.load.cache.CurrencyCache
import com.malomnogo.data.load.cache.CurrencyCacheDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseSettingsRepositoryTest {

    private lateinit var allCacheDataSource: FakeCurrencyCacheDataSource
    private lateinit var favoriteCacheDataSource: FakeLatestCurrencyCacheDataSource
    private lateinit var repository: BaseSettingsRepository

    @Before
    fun setup() {
        allCacheDataSource = FakeCurrencyCacheDataSource()
        favoriteCacheDataSource = FakeLatestCurrencyCacheDataSource()

        repository = BaseSettingsRepository(
            allCacheDataSource = allCacheDataSource,
            favoriteCacheDataSource = favoriteCacheDataSource
        )
    }

    @Test
    fun testAllCurrencies() = runBlocking {
        val expected = listOf("A", "B", "C", "D")
        val actual = repository.currencies()
        assertEquals(expected, actual)
    }

    @Test
    fun save() = runBlocking {
        repository.save(from = "A", to = "B")
        favoriteCacheDataSource.checkSaved(
            listOf(LatestCurrencyCache(from = "A", to = "B"))
        )
        repository.save(from = "C", to = "D")
        favoriteCacheDataSource.checkSaved(
            listOf(
                LatestCurrencyCache(from = "A", to = "B"),
                LatestCurrencyCache(from = "C", to = "D")
            )
        )
    }

    @Test
    fun testCurrenciesDestinations() = runBlocking {
        save()
        var actual = repository.currenciesDestinations(from = "A")
        var expected = listOf("C", "D")
        assertEquals(expected, actual)
        actual = repository.currenciesDestinations(from = "C")
        expected = listOf("A", "B")
        assertEquals(expected, actual)
        actual = repository.currenciesDestinations(from = "D")
        expected = listOf("A", "B", "C")
        assertEquals(expected, actual)
    }
}

private class FakeCurrencyCacheDataSource : CurrencyCacheDataSource.Read {

    override suspend fun read() = listOf(
        CurrencyCache("A", "a"),
        CurrencyCache("B", "b"),
        CurrencyCache("C", "c"),
        CurrencyCache("D", "d")
    )
}

private class FakeLatestCurrencyCacheDataSource : LatestCurrencyCacheDataSource.Mutable {

    private var actual = mutableListOf<LatestCurrencyCache>()

    override suspend fun save(currency: LatestCurrencyCache) {
        actual.add(currency)
    }

    override suspend fun read() = actual

    fun checkSaved(expected: List<LatestCurrencyCache>) {
        assertEquals(expected, actual)
    }
}