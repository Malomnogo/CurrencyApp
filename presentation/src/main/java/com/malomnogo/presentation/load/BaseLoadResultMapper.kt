package com.malomnogo.presentation.load

import com.malomnogo.domain.load.LoadCurrenciesResult
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation

class BaseLoadResultMapper(
    private val observable: LoadUiObservable,
    private val navigation: Navigation,
    private val clear: Clear
) : LoadCurrenciesResult.Mapper {

    override fun mapSuccess() {
        navigation.updateUi(DashboardScreen)
        clear.clear(LoadViewModel::class.java)
    }

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }
}