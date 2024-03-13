package com.malomnogo.presentation.main

import androidx.lifecycle.ViewModel
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.dashboard.DashboardScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigation: Navigation
) : ViewModel() {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            navigation.updateUi(DashboardScreen)
    }

    fun startGettingUpdates(navigation: UpdateUi<Screen>) {
        this.navigation.updateObserver(navigation)
    }

    fun stopGettingUpdates() {
        navigation.updateObserver(UpdateUi.Empty())
    }
}