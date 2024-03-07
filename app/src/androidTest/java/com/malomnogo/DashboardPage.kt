package com.malomnogo

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import ru.easycode.presentation.R

class DashboardPage {

    fun checkVisible() {
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(LinearLayout::class.java)
            )
        ).check(matches(isDisplayed()))
    }

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(LinearLayout::class.java)
            )
        ).check(doesNotExist())
    }

    fun checkEmpty() {
        onView(RecyclerViewMatcher(position = 0, R.id.emptyTextView, R.id.recyclerView))
            .check(matches(withText("Go to settings to add a pair")))
    }

    fun checkPair(position: Int, currencyPair: String, rates: String) {
        onView(RecyclerViewMatcher(position = position, R.id.pairTextView, R.id.recyclerView))
            .check(matches(withText(currencyPair)))
        onView(RecyclerViewMatcher(position = position, R.id.currencyTextView, R.id.recyclerView))
            .check(matches(withText(rates)))
    }

    fun remove(position: Int) {
        onView(
            RecyclerViewMatcher(position = position, R.id.deleteImageButton, R.id.recyclerView)
        ).perform(click())
    }

    fun goToSettings() {
        onView(
            allOf(
                withId(R.id.settingsButton),
                isAssignableFrom(ImageView::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        )
            .check(matches(DrawableMatcher(R.drawable.settings_48px)))
            .perform(click())
    }
}