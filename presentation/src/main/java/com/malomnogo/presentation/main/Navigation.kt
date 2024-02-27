package com.malomnogo.presentation.main

import com.malomnogo.presentation.core.UiObservable


interface Navigation : UiObservable<Screen> {

    class Base : UiObservable.Abstract<Screen>(Screen.Empty), Navigation
}
