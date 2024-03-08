package com.malomnogo.presentation.settings

import com.malomnogo.domain.premium.SaveResult
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.premium.PremiumScreen

class BaseSaveResultMapper(
    private val navigation: Navigation,
    private val clear: Clear
) : SaveResult.Mapper {

    override fun mapSuccess() {
        navigation.updateUi(DashboardScreen)
        clear.clear(SettingsViewModel::class.java)
    }

    override fun mapNeedPremium() {
        navigation.updateUi(PremiumScreen)
    }
}
