package com.malomnogo.presentation.main

import com.malomnogo.presentation.core.CustomViewModel
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.load.LoadScreen

class MainViewModel(
    private val navigation: Navigation
) : CustomViewModel {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            navigation.updateUi(LoadScreen)
    }

    fun startGettingUpdates(navigation: UpdateUi<Screen>) {
        this.navigation.updateObserver(navigation)
    }

    fun stopGettingUpdates() {
        navigation.updateObserver(UpdateUi.Empty())
    }
}