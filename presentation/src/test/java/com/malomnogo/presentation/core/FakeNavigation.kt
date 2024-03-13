package com.malomnogo.presentation.core

import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.main.Screen
import com.malomnogo.presentation.premium.PremiumScreen
import com.malomnogo.presentation.settings.SettingsScreen
import org.junit.Assert.assertEquals

class FakeNavigation : Navigation {

    private var actual: Screen = Screen.Empty
    private var observer: UpdateUi<Screen> = UpdateUi.Empty()

    fun checkNavigateToPremium() {
        assertEquals(PremiumScreen, actual)
    }

    fun checkNavigateToDashboard() {
        assertEquals(DashboardScreen, actual)
    }

    fun checkNavigateToSettings() {
        assertEquals(SettingsScreen, actual)
    }

    fun checkPop() {
        assertEquals(Screen.Pop, actual)
    }

    fun checkNotCalled() {
        assertEquals(Screen.Empty, actual)
    }

    override fun updateObserver(observer: UpdateUi<Screen>) {
        this.observer = observer
    }

    override fun updateUi(uiState: Screen) {
        actual = uiState
    }
}