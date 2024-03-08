package com.malomnogo.presentation.premium

import com.malomnogo.presentation.core.UiObservable

interface PremiumObservable : UiObservable<PremiumUiState> {

    class Base : UiObservable.Abstract<PremiumUiState>(PremiumUiState.Empty), PremiumObservable
}