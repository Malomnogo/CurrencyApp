package com.malomnogo.presentation.dashboard

import com.malomnogo.presentation.core.UiObservable
import javax.inject.Inject

interface DashboardUiObservable : UiObservable<DashboardUiState> {

    class Base @Inject constructor() : UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty), DashboardUiObservable
}