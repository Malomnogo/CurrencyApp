package com.malomnogo

import com.malomnogo.domain.premium.PremiumInteractor
import com.malomnogo.domain.premium.PremiumStorage
import com.malomnogo.domain.premium.SaveResult
import com.malomnogo.domain.settings.SettingsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BasePremiumInteractorTest {

    private lateinit var interactor: PremiumInteractor
    private lateinit var repository: FakeSettingsRepository
    private lateinit var premiumStorage: FakeReadPremiumStorage

    @Before
    fun setup() {
        repository = FakeSettingsRepository()
        premiumStorage = FakeReadPremiumStorage()
        interactor = PremiumInteractor.Base(
            repository = repository,
            premiumStorage = premiumStorage,
            maxPairs = 2
        )
    }

    @Test
    fun scenarioTest(): Unit = runBlocking {
        val actualAll = interactor.currencies()
        assertEquals(listOf("USD", "EUR", "JPY"), actualAll)

        var actualDestinations = interactor.currenciesDestinations("USD")
        assertEquals(listOf("EUR", "JPY"), actualDestinations)

        var actualSave = interactor.save("USD", "EUR")
        assertEquals(SaveResult.Success, actualSave)
        repository.checkSavedCurrencyPairs(Pair("USD", "EUR"))

        actualDestinations = interactor.currenciesDestinations("USD")
        assertEquals(listOf("JPY"), actualDestinations)

        actualSave = interactor.save("USD", "JPY")
        assertEquals(SaveResult.Success, actualSave)
        repository.checkSavedCurrencyPairs(Pair("USD", "EUR"), Pair("USD", "JPY"))

        actualSave = interactor.save("EUR", "JPY")
        assertEquals(SaveResult.NeedPremium, actualSave)
        repository.checkSavedCurrencyPairs(Pair("USD", "EUR"), Pair("USD", "JPY"))

        premiumStorage.premium()
        actualSave = interactor.save("EUR", "JPY")
        assertEquals(SaveResult.Success, actualSave)
        repository.checkSavedCurrencyPairs(
            Pair("USD", "EUR"),
            Pair("USD", "JPY"),
            Pair("EUR", "JPY")
        )

        repository.removeLastOne()
        actualSave = interactor.save("EUR", "JPY")
        assertEquals(SaveResult.Success, actualSave)
        repository.checkSavedCurrencyPairs(
            Pair("USD", "EUR"),
            Pair("USD", "JPY"),
            Pair("EUR", "JPY")
        )
    }
}

private class FakeSettingsRepository : SettingsRepository {

    private val currencies = mutableListOf("USD", "EUR", "JPY")

    override suspend fun currencies() = currencies

    override suspend fun currenciesDestinations(from: String): List<String> {
        currencies.remove(from)
        currencies.removeAll(
            savedPairs.filter { pair -> pair.first == from }.map { pair -> pair.second }
        )
        return currencies
    }

    private val savedPairs = mutableListOf<Pair<String, String>>()

    override suspend fun save(from: String, to: String) {
        savedPairs.add(Pair(from, to))
    }

    override suspend fun savedPairsCount() = savedPairs.size

    fun removeLastOne() {
        savedPairs.removeLast()
    }

    fun checkSavedCurrencyPairs(vararg pairs: Pair<String, String>) {
        assertEquals(pairs.toList(), savedPairs)
    }
}

private class FakeReadPremiumStorage : PremiumStorage.Read {

    private var isPremium = false

    override fun read() = isPremium

    fun premium() {
        isPremium = true
    }
}