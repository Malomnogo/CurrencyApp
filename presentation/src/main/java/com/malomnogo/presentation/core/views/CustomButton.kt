package com.malomnogo.presentation.core.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class CustomButton : MaterialButton, CustomButtonActions {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        freezesText = true
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState().let {
            val state = VisibilitySavedState(it)
            state.save(this)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as VisibilitySavedState?
        super.onRestoreInstanceState(restoredState?.superState)
        state?.restore(this)
    }

    override fun show() {
        visibility = VISIBLE
    }

    override fun hide() {
        visibility = GONE
    }

    override fun changeText(text: String) {
        this.text = text
    }
}

interface CustomButtonActions : ChangeVisibility, ChangeText