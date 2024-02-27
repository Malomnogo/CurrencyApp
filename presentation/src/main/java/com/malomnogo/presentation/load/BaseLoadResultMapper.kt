package com.malomnogo.presentation.load

import com.malomnogo.domain.LoadCurrenciesResult
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation

class BaseLoadResultMapper(
    private val observable: UpdateUi<LoadUiState>,
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