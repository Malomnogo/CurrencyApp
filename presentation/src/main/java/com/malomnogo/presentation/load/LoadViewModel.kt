package com.malomnogo.presentation.load

import com.malomnogo.domain.LoadCurrenciesRepository
import com.malomnogo.domain.LoadCurrenciesResult
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation

class LoadViewModel(
    private val repository: LoadCurrenciesRepository,
    private val uiObservable: LoadUiObservable,
    private val navigation: Navigation,
    private val clear: Clear,
    runAsync: RunAsync,
    private val mapper: LoadCurrenciesResult.Mapper = BaseLoadResultMapper(
        uiObservable,
        navigation,
        clear
    )
) : BaseViewModel(runAsync) {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) load()
    }

    fun load() {
        uiObservable.updateUi(LoadUiState.Progress)
        runAsync({
            repository.fetchCurrencies()
        }) {
            it.map(mapper)
        }
    }

    fun startGettingUpdates(observer: UpdateUi<LoadUiState>) {
        uiObservable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UpdateUi.Empty())
    }
}