package com.malomnogo.presentation.premium

import com.malomnogo.domain.premium.BuyPremiumResult
import com.malomnogo.presentation.main.Navigation
import com.malomnogo.presentation.main.Screen
import javax.inject.Inject

class BaseBuyPremiumResultMapper @Inject constructor(
    private val premiumObservable: PremiumObservable,
    private val navigation: Navigation,
) : BuyPremiumResult.Mapper {

    override fun mapSuccess(message: String) {
        navigation.updateUi(Screen.Pop)
    }

    override fun mapError(message: String) {
        premiumObservable.updateUi(PremiumUiState.Error(message))
    }
}