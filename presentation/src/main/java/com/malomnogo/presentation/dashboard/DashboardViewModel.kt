package com.malomnogo.presentation.dashboard

import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.settings.SettingsScreen

class DashboardViewModel(
    private val navigation: Navigation,
    private val observable: DashboardUiObservable,
    private val repository: DashboardRepository,
    private val clear: Clear,
    runAsync: RunAsync,
    private val mapper: DashboardResult.Mapper = BaseDashboardResultMapper(observable)
) : BaseViewModel(runAsync), Retry {

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboardItems()
        }) {
            it.map(mapper)
        }
    }

    fun goToSettings() {
        navigation.updateUi(SettingsScreen)
        clear.clear(this::class.java)
    }

    override fun retry() {
        load()
    }

    fun startGettingUpdates(observer: UpdateUi<DashboardUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}