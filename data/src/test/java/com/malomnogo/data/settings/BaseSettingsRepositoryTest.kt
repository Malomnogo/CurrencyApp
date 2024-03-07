package com.malomnogo.data.settings

import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.load.cache.CurrenciesCacheDataSource
import com.malomnogo.data.load.cache.CurrencyCache
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseSettingsRepositoryTest {

    private lateinit var allCacheDataSource: FakeCurrencyCacheDataSource
    private lateinit var favoriteCacheDataSource: FakeCurrencyPairCacheDataSource
    private lateinit var repository: BaseSettingsRepository

    @Before
    fun setup() {
        allCacheDataSource = FakeCurrencyCacheDataSource()
        favoriteCacheDataSource = FakeCurrencyPairCacheDataSource()

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
            listOf(CurrencyPairCache(from = "A", to = "B"))
        )
        repository.save(from = "C", to = "D")
        favoriteCacheDataSource.checkSaved(
            listOf(
                CurrencyPairCache(from = "A", to = "B"),
                CurrencyPairCache(from = "C", to = "D")
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

private class FakeCurrencyCacheDataSource : CurrenciesCacheDataSource.Read {

    override suspend fun read() = listOf(
        CurrencyCache("A", "a"),
        CurrencyCache("B", "b"),
        CurrencyCache("C", "c"),
        CurrencyCache("D", "d")
    )
}

private class FakeCurrencyPairCacheDataSource : CurrencyPairCacheDataSource.Mutable {

    private val actual = mutableListOf<CurrencyPairCache>()

    override suspend fun save(currency: CurrencyPairCache) {
        actual.add(currency)
    }

    //not used in test
    override suspend fun remove(from: String, to: String) = Unit

    override suspend fun read() = actual

    fun checkSaved(expected: List<CurrencyPairCache>) {
        assertEquals(expected, actual)
    }
}