package com.malomnogo.presentation.settings

import com.malomnogo.presentation.core.UiObservable

interface SettingsUiObservable : UiObservable<SettingsUiState> {

    class Base : UiObservable.Abstract<SettingsUiState>(SettingsUiState.Empty), SettingsUiObservable
}