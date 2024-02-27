package com.malomnogo.presentation.load

import com.malomnogo.presentation.core.UiObservable

interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base : UiObservable.Abstract<LoadUiState>(LoadUiState.Empty), LoadUiObservable
}