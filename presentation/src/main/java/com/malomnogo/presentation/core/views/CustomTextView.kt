package com.malomnogo.presentation.core.views

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView : AppCompatTextView, CustomTextActions {

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

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = TextViewSavedState(it)
            state.save(this)
            state.save(this.currentTextColor)
            state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoreState = state as TextViewSavedState?
        super.onRestoreInstanceState(restoreState?.superState)
        restoreState?.let { restored ->
            restored.restore(this)
            this.setTextColor(restored.restore())
        }
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

    override fun changeTextColor(textColor: String) {
        this.setTextColor(Color.parseColor(textColor))
    }
}

interface CustomTextActions : ChangeVisibility, ChangeText, ChangeTextColor