package com.malomnogo.presentation.dashboard

import com.malomnogo.presentation.core.UiObservable

interface DashboardUiObservable : UiObservable<DashboardUiState> {

    class Base : UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty), DashboardUiObservable
}