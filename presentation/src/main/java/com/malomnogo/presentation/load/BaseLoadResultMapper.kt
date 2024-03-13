package com.malomnogo.presentation.load

import com.malomnogo.domain.load.LoadCurrenciesResult
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Navigation
import javax.inject.Inject

class BaseLoadResultMapper @Inject constructor(
    private val observable: LoadUiObservable,
    private val navigation: Navigation,
) : LoadCurrenciesResult.Mapper {

    override fun mapSuccess() {
        navigation.updateUi(DashboardScreen)
    }

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }
}