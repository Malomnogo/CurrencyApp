package com.malomnogo

import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.malomnogo.presentation.core.views.CustomButton
import com.malomnogo.presentation.core.views.ErrorTextView
import org.hamcrest.CoreMatchers.allOf
import ru.easycode.presentation.R

class LoadingPage {

    fun checkVisible() {
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(FrameLayout::class.java)
            )
        ).check(matches(isDisplayed()))
    }

    fun checkError(message: String) {
        onView(
            allOf(
                withId(R.id.errorTextView),
                withText(message),
                isAssignableFrom(ErrorTextView::class.java),
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.retryButton),
                isAssignableFrom(CustomButton::class.java)
            )
        ).check(matches(isDisplayed()))
    }

    fun clickRetry() {
        onView(
            allOf(
                withId(R.id.retryButton),
                isAssignableFrom(CustomButton::class.java)
            )
        ).perform(click())
    }

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(FrameLayout::class.java)
            )
        ).check(doesNotExist())
    }
}