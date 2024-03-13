package com.malomnogo.presentation.premium

import com.malomnogo.presentation.core.UiObservable
import javax.inject.Inject

interface PremiumObservable : UiObservable<PremiumUiState> {

    class Base @Inject constructor()
        : UiObservable.Abstract<PremiumUiState>(PremiumUiState.Empty), PremiumObservable
}