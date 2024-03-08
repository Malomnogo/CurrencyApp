package com.malomnogo.presentation.premium

import com.malomnogo.domain.premium.BuyPremiumResult
import com.malomnogo.domain.premium.PremiumRepository
import com.malomnogo.presentation.core.FakeClear
import com.malomnogo.presentation.core.FakeNavigation
import com.malomnogo.presentation.core.FakeRunAsync
import com.malomnogo.presentation.core.UpdateUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class PremiumViewModelTest {

    private lateinit var viewModel: PremiumViewModel
    private lateinit var observable: FakeObservable
    private lateinit var navigation: FakeNavigation
    private lateinit var repository: FakeRepository
    private lateinit var clear: FakeClear
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        repository = FakeRepository()
        observable = FakeObservable()
        navigation = FakeNavigation()
        clear = FakeClear()
        runAsync = FakeRunAsync()
        viewModel = PremiumViewModel(
            repository = repository,
            observable = observable,
            navigation = navigation,
            clear = clear,
            runAsync = runAsync
        )
    }

    @Test
    fun test() {
        viewModel.init(true)
        observable.checkInitial()

        repository.expectError()
        viewModel.buy()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkError()

        repository.expectSuccess()
        viewModel.buy()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkSuccess()

        viewModel.navigateToSettings()
        navigation.checkPop()
        clear.checkCalled(PremiumViewModel::class.java)
    }

    @Test
    fun lifecycle() {
        viewModel.init(isFirstRun = false)
        navigation.checkNotCalled()
        val observer: UpdateUi<PremiumUiState> = object : UpdateUi<PremiumUiState> {
            override fun updateUi(uiState: PremiumUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        observable.checkEmpty(observer)
    }
}

private class FakeObservable : PremiumObservable {

    private var actualUiSate: PremiumUiState = PremiumUiState.Empty
    private var actualObserver: UpdateUi<PremiumUiState> = UpdateUi.Empty()

    override fun updateUi(uiState: PremiumUiState) {
        actualUiSate = uiState
    }

    override fun updateObserver(observer: UpdateUi<PremiumUiState>) {
        actualObserver = observer
    }

    fun checkObserver(observer: UpdateUi<PremiumUiState>) {
        assertEquals(observer, actualObserver)
    }

    fun checkEmpty(observer: UpdateUi<PremiumUiState>) {
        assertNotEquals(observer, actualObserver)
    }

    fun checkInitial() {
        assertEquals(PremiumUiState.Initial("maximum is 1"), actualUiSate)
    }

    fun checkProgress() {
        assertEquals(PremiumUiState.Progress, actualUiSate)
    }

    fun checkSuccess() {
        assertEquals(PremiumUiState.Success("Success"), actualUiSate)
    }

    fun checkError() {
        assertEquals(PremiumUiState.Error(message = "Service unavailable"), actualUiSate)
    }
}

private class FakeRepository : PremiumRepository {

    private lateinit var actualResult: BuyPremiumResult

    override suspend fun buy(): BuyPremiumResult {
        return actualResult
    }

    override fun description() = "maximum is 1"

    fun expectSuccess() {
        actualResult = BuyPremiumResult.Success("Success")
    }

    fun expectError() {
        actualResult = BuyPremiumResult.Error(message = "Service unavailable")
    }
}