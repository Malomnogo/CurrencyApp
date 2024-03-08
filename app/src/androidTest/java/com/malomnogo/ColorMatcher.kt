package com.malomnogo

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class ColorMatcher(private val color: Int) :
    BoundedMatcher<View, TextView>(TextView::class.java) {

    constructor(colorString: String) : this(Color.parseColor(colorString))

    override fun describeTo(description: Description) {
        description.appendText("color for text")
    }

    override fun matchesSafely(item: TextView) = item.currentTextColor == color
}
