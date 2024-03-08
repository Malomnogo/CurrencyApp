package com.malomnogo.presentation.premium

import com.malomnogo.domain.premium.BuyPremiumResult
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.main.Screen

class BaseBuyPremiumResultMapper(
    private val premiumObservable: PremiumObservable,
    private val navigation: Navigation,
    private val clear: Clear
) : BuyPremiumResult.Mapper {

    override fun mapSuccess(message: String) {
        navigation.updateUi(Screen.Pop)
        clear.clear(PremiumViewModel::class.java)
    }

    override fun mapError(message: String) {
        premiumObservable.updateUi(PremiumUiState.Error(message))
    }
}