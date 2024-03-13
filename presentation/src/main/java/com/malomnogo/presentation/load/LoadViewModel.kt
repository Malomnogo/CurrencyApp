package com.malomnogo.presentation.load

import com.malomnogo.domain.load.LoadCurrenciesRepository
import com.malomnogo.domain.load.LoadCurrenciesResult
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadViewModel @Inject constructor(
    private val repository: LoadCurrenciesRepository,
    private val uiObservable: LoadUiObservable,
    runAsync: RunAsync,
    private val mapper: LoadCurrenciesResult.Mapper
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