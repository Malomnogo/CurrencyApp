package com.malomnogo.presentation.premium

import com.malomnogo.domain.premium.BuyPremiumResult
import com.malomnogo.domain.premium.PremiumRepository
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.main.Screen

class PremiumViewModel(
    private val repository: PremiumRepository,
    private val observable: PremiumObservable,
    private val navigation: Navigation,
    private val clear: Clear,
    runAsync: RunAsync,
    private val mapper: BuyPremiumResult.Mapper =
        BaseBuyPremiumResultMapper(observable, navigation, clear)
) : BaseViewModel(runAsync) {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            observable.updateUi(PremiumUiState.Initial(repository.description()))
    }

    fun buy() {
        observable.updateUi(PremiumUiState.Progress)
        runAsync({
            repository.buy()
        }) {
            it.map(mapper)
        }
    }

    fun navigateToSettings() {
        navigation.updateUi(Screen.Pop)
        clear.clear(PremiumViewModel::class.java)
    }

    fun startGettingUpdates(observer: UpdateUi<PremiumUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}