package com.malomnogo.presentation.core.views

import android.graphics.Color.BLACK
import android.os.Parcel
import android.os.Parcelable

class TextViewSavedState : AbstractVisibilitySavedState {

    private var color = BLACK

    fun restore() = color

    fun save(color: Int) {
        this.color = color
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(color)
    }

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        color = parcelIn.readInt()
    }

    companion object CREATOR : Parcelable.Creator<TextViewSavedState> {

        override fun createFromParcel(parcel: Parcel): TextViewSavedState =
            TextViewSavedState(parcel)

        override fun newArray(size: Int): Array<TextViewSavedState?> = arrayOfNulls(size)
    }
}