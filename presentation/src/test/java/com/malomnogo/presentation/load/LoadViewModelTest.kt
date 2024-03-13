package com.malomnogo.presentation.load

import com.malomnogo.domain.load.LoadCurrenciesRepository
import com.malomnogo.domain.load.LoadCurrenciesResult
import com.malomnogo.presentation.core.FakeNavigation
import com.malomnogo.presentation.core.FakeRunAsync
import com.malomnogo.presentation.core.UpdateUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {

    private lateinit var viewModel: LoadViewModel
    private lateinit var uiObservable: FakeUiObservable
    private lateinit var repository: FakeRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var navigation: FakeNavigation

    @Before
    fun setup() {
        uiObservable = FakeUiObservable()
        runAsync = FakeRunAsync()
        navigation = FakeNavigation()
        repository = FakeRepository()
        viewModel = LoadViewModel(
            repository = repository,
            uiObservable = uiObservable,
            runAsync = runAsync,
            mapper = BaseLoadResultMapper(uiObservable, navigation)
        )
    }

    @Test
    fun success() {
        repository.expectSuccess()
        viewModel.init(isFirstRun = true)
        uiObservable.checkProgress()
        runAsync.returnResult()
        navigation.checkNavigateToDashboard()
    }

    @Test
    fun errorThenSuccess() {
        repository.expectError()
        viewModel.init(isFirstRun = true)
        uiObservable.checkProgress()
        runAsync.returnResult()
        uiObservable.checkError()

        repository.expectSuccess()
        viewModel.load()
        uiObservable.checkProgress()
        runAsync.returnResult()
        navigation.checkNavigateToDashboard()
    }

    @Test
    fun lifecycle() {
        viewModel.init(isFirstRun = false)
        navigation.checkNotCalled()
        val observer: UpdateUi<LoadUiState> = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        uiObservable.checkObserver(observer)

        viewModel.stopGettingUpdates()
        uiObservable.checkEmpty(observer)
    }
}

private class FakeRepository : LoadCurrenciesRepository {

    private lateinit var loadResult: LoadCurrenciesResult

    override suspend fun fetchCurrencies() = loadResult

    fun expectSuccess() {
        loadResult = LoadCurrenciesResult.Success
    }

    fun expectError() {
        loadResult = LoadCurrenciesResult.Error(message = "No internet connection")
    }
}

private class FakeUiObservable : LoadUiObservable {

    private var actualUiSate: LoadUiState = LoadUiState.Empty
    private var actualObserver: UpdateUi<LoadUiState> = UpdateUi.Empty()

    override fun updateUi(uiState: LoadUiState) {
        actualUiSate = uiState
    }

    override fun updateObserver(observer: UpdateUi<LoadUiState>) {
        actualObserver = observer
    }

    fun checkObserver(observer: UpdateUi<LoadUiState>) {
        assertEquals(observer, actualObserver)
    }

    fun checkEmpty(observer: UpdateUi<LoadUiState>) {
        assertNotEquals(observer, actualObserver)
    }

    fun checkProgress() {
        assertEquals(LoadUiState.Progress, actualUiSate)
    }

    fun checkError() {
        assertEquals(LoadUiState.Error(message = "No internet connection"), actualUiSate)
    }
}