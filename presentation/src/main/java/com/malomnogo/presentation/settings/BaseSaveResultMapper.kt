package com.malomnogo.presentation.settings

import com.malomnogo.domain.premium.SaveResult
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.premium.PremiumScreen
import javax.inject.Inject

class BaseSaveResultMapper @Inject constructor(
    private val navigation: Navigation
) : SaveResult.Mapper {

    override fun mapSuccess() {
        navigation.updateUi(DashboardScreen)
    }

    override fun mapNeedPremium() {
        navigation.updateUi(PremiumScreen)
    }
}
