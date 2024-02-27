package com.malomnogo.presentation.main

import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.load.LoadScreen
import org.junit.Assert.assertEquals

class FakeNavigation : Navigation {

    private var actual: Screen = Screen.Empty
    private var observer: UpdateUi<Screen> = UpdateUi.Empty()

    fun checkNavigateToLoad() {
        assertEquals(LoadScreen, actual)
    }

    fun checkNavigateToDashboard() {
        assertEquals(DashboardScreen, actual)
    }

    fun checkNotCalled() {
        assertEquals(Screen.Empty, actual)
    }

    override fun clear() {
        actual = Screen.Empty
    }

    override fun updateObserver(observer: UpdateUi<Screen>) {
        this.observer = observer
    }

    override fun updateUi(uiState: Screen) {
        actual = uiState
    }
}