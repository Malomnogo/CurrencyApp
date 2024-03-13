package com.malomnogo.presentation.main

import com.malomnogo.presentation.core.UiObservable
import javax.inject.Inject


interface Navigation : UiObservable<Screen> {

    class Base @Inject constructor() : UiObservable.Abstract<Screen>(Screen.Empty), Navigation
}
