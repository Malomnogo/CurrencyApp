package com.malomnogo.presentation.dashboard

import android.util.Log
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.settings.SettingsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val navigation: Navigation,
    private val observable: DashboardUiObservable,
    private val repository: DashboardRepository,
    runAsync: RunAsync,
    private val mapper: DashboardResult.Mapper<DashboardUiState>,
    private val delimiter: Delimiter.Split
) : BaseViewModel(runAsync), ClickActions {

    init {
        Log.d("tag", "init")
    }

    suspend fun loadInternal() {
        runAsync({
            repository.loadDashboardItems()
        }) {
            observable.updateUi(it.map(mapper))
        }
    }

    fun load() {
        observable.updateUi(DashboardUiState.Progress)
        runAsync({
            repository.dashboardItems()
        }) {
            observable.updateUi(it.map(mapper))
        }
    }

    fun goToSettings() {
        navigation.updateUi(SettingsScreen)
    }

    override fun retry() {
        load()
    }

    override fun remove(pair: String) {
        runAsync({
            val split = delimiter.split(pair)
            repository.removePair(split.first, split.second)
        }) {
            observable.updateUi(it.map(mapper))
        }
    }

    fun startGettingUpdates(observer: UpdateUi<DashboardUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}