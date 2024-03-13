package com.malomnogo.presentation.premium

import com.malomnogo.domain.premium.BuyPremiumResult
import com.malomnogo.domain.premium.PremiumRepository
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val repository: PremiumRepository,
    private val observable: PremiumObservable,
    private val navigation: Navigation,
    runAsync: RunAsync,
    private val mapper: BuyPremiumResult.Mapper =
        BaseBuyPremiumResultMapper(observable, navigation)
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
    }

    fun startGettingUpdates(observer: UpdateUi<PremiumUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}