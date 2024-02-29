package com.malomnogo.presentation.dashboard

import com.malomnogo.domain.dashboard.DashboardItem
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult
import com.malomnogo.presentation.core.FakeClear
import com.malomnogo.presentation.core.FakeNavigation
import com.malomnogo.presentation.core.FakeRunAsync
import com.malomnogo.presentation.core.UpdateUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var navigation: FakeNavigation
    private lateinit var observable: FakeDashboardUiObservable
    private lateinit var repository: FakeDashboardRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var clear: FakeClear

    @Before
    fun setup() {
        navigation = FakeNavigation()
        observable = FakeDashboardUiObservable()
        repository = FakeDashboardRepository()
        runAsync = FakeRunAsync()
        clear = FakeClear()
        viewModel = DashboardViewModel(
            navigation = navigation,
            observable = observable,
            repository = repository,
            clear = clear,
            runAsync = runAsync
        )
    }

    @Test
    fun testEmpty() {
        repository.empty()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkEmpty()
    }

    @Test
    fun testSuccessThenGoToSettings() {
        repository.success()
        viewModel.load()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkSuccess()
        viewModel.goToSettings()
        navigation.checkNavigateToSettings()
        clear.checkCalled(DashboardViewModel::class.java)
    }

    @Test
    fun testErrorThenSuccess() {
        repository.error()

        viewModel.load()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkError()

        repository.success()
        viewModel.retry()
        observable.checkProgress()
        runAsync.returnResult()
        observable.checkSuccess()
    }

    @Test
    fun lifecycle() {
        repository.success()
        viewModel.load()
        navigation.checkNotCalled()
        val observer: UpdateUi<DashboardUiState> = object : UpdateUi<DashboardUiState> {
            override fun updateUi(uiState: DashboardUiState) = Unit
        }
        viewModel.startGettingUpdates(observer = observer)
        observable.checkObserver(observer = observer)

        viewModel.stopGettingUpdates()
        observable.checkEmptyObserver(observer = observer)
    }
}

private class FakeDashboardUiObservable : DashboardUiObservable {

    private var actualUiSate: DashboardUiState = DashboardUiState.Empty
    private var actualObserver: UpdateUi<DashboardUiState> = UpdateUi.Empty()

    override fun updateObserver(observer: UpdateUi<DashboardUiState>) {
        actualObserver = observer
    }

    override fun updateUi(uiState: DashboardUiState) {
        actualUiSate = uiState
    }

    fun checkProgress() {
        val expected = DashboardUiState.Progress
        assertEquals(expected, actualUiSate)
    }

    fun checkEmpty() {
        val expected = DashboardUiState.Empty
        assertEquals(expected, actualUiSate)
    }

    fun checkSuccess() {
        val expected = DashboardUiState.Base(
            listOf(
                DashboardUi.Base(pair = "A/B", rate = "123.4")
            )
        )
        assertEquals(expected, actualUiSate)
    }

    fun checkError() {
        val expected = DashboardUiState.Error(message = "No internet connection")
        assertEquals(expected, actualUiSate)
    }

    fun checkEmptyObserver(observer: UpdateUi<DashboardUiState>) {
        assertNotEquals(observer, actualObserver)
    }

    fun checkObserver(observer: UpdateUi<DashboardUiState>) {
        assertEquals(observer, actualObserver)
    }
}

private class FakeDashboardRepository : DashboardRepository {

    private lateinit var result: DashboardResult

    override suspend fun dashboardItems() = result

    fun empty() {
        result = DashboardResult.Empty
    }

    fun success() {
        result = DashboardResult.Success(
            listOf(DashboardItem.Base(from = "A", to = "B", rates = 123.4))
        )
    }

    fun error() {
        result = DashboardResult.Error(message = "No internet connection")
    }
}