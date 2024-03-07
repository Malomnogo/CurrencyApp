package com.malomnogo.presentation.settings

import com.malomnogo.domain.settings.SettingsRepository
import com.malomnogo.presentation.core.FakeClear
import com.malomnogo.presentation.core.FakeNavigation
import com.malomnogo.presentation.core.FakeRunAsync
import com.malomnogo.presentation.core.UpdateUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var observable: FakeSettingsUiObservable
    private lateinit var repository: FakeSettingsRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var clear: FakeClear
    private lateinit var bundleWrapper: FakeBundleWrapper

    @Before
    fun setup() {
        navigation = FakeNavigation()
        observable = FakeSettingsUiObservable()
        repository = FakeSettingsRepository()
        runAsync = FakeRunAsync()
        clear = FakeClear()
        bundleWrapper = FakeBundleWrapper()
        viewModel = SettingsViewModel(
            navigation = navigation,
            observable = observable,
            repository = repository,
            runAsync = runAsync,
            clear = clear
        )
    }

    @Test
    fun test() {
        viewModel.init(bundleWrapper)
        runAsync.returnResult()
        observable.checkInitial()

        viewModel.choose(from = "USD")
        runAsync.returnResult()
        observable.checkDestinationCurrencies("JPY", "EUR")

        viewModel.chooseDestination(from = "USD", to = "JPY")
        runAsync.returnResult()
        observable.checkReadyToSave(
            toList = listOf(
                CurrencyChoiceUi.Base("JPY", true),
                CurrencyChoiceUi.Base("EUR", false)
            )
        )

        viewModel.save(from = "USD", to = "JPY")
        runAsync.returnResult()
        repository.checkSaved(from = "USD", to = "JPY")
        navigation.checkNavigateToDashboard()
        clear.checkCalled(SettingsViewModel::class.java)

        viewModel.init(bundleWrapper)
        runAsync.returnResult()
        observable.checkInitial()

        viewModel.choose(from = "USD")
        runAsync.returnResult()
        observable.checkDestinationCurrencies("EUR")

        viewModel.chooseDestination(from = "USD", to = "EUR")
        runAsync.returnResult()
        observable.checkReadyToSave(
            toList = listOf(CurrencyChoiceUi.Base("EUR", true))
        )

        viewModel.save(from = "USD", to = "EUR")
        runAsync.returnResult()
        repository.checkSaved(from = "USD", to = "EUR")
        navigation.checkNavigateToDashboard()
        clear.checkCalled(SettingsViewModel::class.java)

        viewModel.init(bundleWrapper)
        runAsync.returnResult()
        observable.checkInitial()

        viewModel.choose(from = "USD")
        runAsync.returnResult()
        observable.checkHint()

        viewModel.navigateToDashboard()
        navigation.checkNavigateToDashboard()
        clear.checkCalled(SettingsViewModel::class.java)
    }

    @Test
    fun lifecycle() {
        bundleWrapper.isEmptyBundle = false
        viewModel.init(bundleWrapper)
        navigation.checkNotCalled()
        val observer: UpdateUi<SettingsUiState> = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer = observer)

        viewModel.stopGettingUpdates()
        observable.checkEmptyObserver(observer = observer)
    }
}

private class FakeSettingsUiObservable : SettingsUiObservable {

    var actual: SettingsUiState = SettingsUiState.Empty
    var actualObserver: UpdateUi<SettingsUiState> = UpdateUi.Empty()

    override fun updateObserver(observer: UpdateUi<SettingsUiState>) {
        actualObserver = observer
    }

    override fun updateUi(uiState: SettingsUiState) {
        actual = uiState
    }

    fun checkEmptyObserver(observer: UpdateUi<SettingsUiState>) {
        assertNotEquals(observer, actualObserver)
    }

    fun checkObserver(observer: UpdateUi<SettingsUiState>) {
        assertEquals(observer, actualObserver)
    }

    fun checkInitial() {
        val expected = SettingsUiState.Initial(
            listOf(
                CurrencyChoiceUi.Base("USD", false),
                CurrencyChoiceUi.Base("JPY", false),
                CurrencyChoiceUi.Base("EUR", false)
            )
        )
        assertEquals(expected, actual)
    }

    fun checkDestinationCurrencies(vararg destinations: String) {
        val expected = SettingsUiState.Destinations(
            fromList = listOf(
                CurrencyChoiceUi.Base("USD", true),
                CurrencyChoiceUi.Base("JPY", false),
                CurrencyChoiceUi.Base("EUR", false)
            ),
            toList = destinations.map { CurrencyChoiceUi.Base(it, false) }
        )
        assertEquals(expected, actual)
    }

    fun checkReadyToSave(toList: List<CurrencyChoiceUi.Base>) {
        val expected = SettingsUiState.ReadyToSave(
            toList = toList
        )
        assertEquals(expected, actual)
    }

    fun checkHint() {
        val expected = SettingsUiState.Hint(
            fromList = listOf(
                CurrencyChoiceUi.Base("USD", true),
                CurrencyChoiceUi.Base("JPY", false),
                CurrencyChoiceUi.Base("EUR", false)
            )
        )
        assertEquals(expected, actual)
    }
}

private class FakeSettingsRepository : SettingsRepository {

    private var actualPair = ""
    private var actualDestinations = mutableListOf("USD", "JPY", "EUR")

    override suspend fun currencies() = mutableListOf("USD", "JPY", "EUR")

    override suspend fun currenciesDestinations(from: String) =
        actualDestinations.apply { remove(from) }

    override suspend fun save(from: String, to: String) {
        actualPair = "$from/$to"
        actualDestinations.remove(to)
    }

    fun checkSaved(from: String, to: String) {
        val expected = "$from/$to"
        assertEquals(expected, actualPair)
    }
}

internal class FakeBundleWrapper : BundleWrapper.Mutable {

    var isEmptyBundle = true

    private var cache = Pair("A", "B")

    override fun isEmpty() = isEmptyBundle

    override fun save(from: String, to: String) {
        cache = Pair(from, to)
    }

    override fun restore() = cache
}