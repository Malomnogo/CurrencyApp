package com.malomnogo.presentation.dashboard

import android.util.Log
import com.malomnogo.presentation.core.UiObservable
import javax.inject.Inject

interface DashboardUiObservable : UiObservable<DashboardUiState> {

    class Base @Inject constructor() :
        UiObservable.Abstract<DashboardUiState>(DashboardUiState.Empty), DashboardUiObservable {
        init {
            Log.d("dLog", "observable init")
        }
    }
}