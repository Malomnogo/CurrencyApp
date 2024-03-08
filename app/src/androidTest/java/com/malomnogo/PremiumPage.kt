package com.malomnogo

import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.malomnogo.presentation.core.views.CustomButton
import org.hamcrest.CoreMatchers.allOf
import ru.easycode.presentation.R

class PremiumPage {

    fun checkVisible() {
        onView(
            allOf(
                withId(R.id.premiumTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.premiumRootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        )
//            .check(matches(ColorMatcher("#000000")))
            .check(matches(isDisplayed()))
    }

    fun clickBuy() {
        onView(
            allOf(
                withId(R.id.buyButton),
                isAssignableFrom(CustomButton::class.java),
                withParent(withId(R.id.premiumRootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java)),
            )
        ).perform(click())
    }

    fun clickNo() {
        onView(
            allOf(
                withId(R.id.noButton),
                isAssignableFrom(CustomButton::class.java),
                withParent(withId(R.id.premiumRootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java)),
            )
        ).perform(click())
    }

    fun checkError() {
        onView(
            allOf(
                withText("Fail purchased, please try again"),
                withId(R.id.premiumTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(R.id.premiumRootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        )
            .check(matches(ColorMatcher("#FF0000")))
            .check(matches(isDisplayed()))
    }

    fun checkSuccessMessage() {
        onView(withText("Success purchased")).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }
}