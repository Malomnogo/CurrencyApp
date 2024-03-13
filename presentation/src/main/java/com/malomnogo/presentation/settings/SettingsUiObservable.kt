package com.malomnogo.presentation.settings

import com.malomnogo.presentation.core.UiObservable
import javax.inject.Inject

interface SettingsUiObservable : UiObservable<SettingsUiState> {

    class Base @Inject constructor() : UiObservable.Abstract<SettingsUiState>(SettingsUiState.Empty), SettingsUiObservable
}