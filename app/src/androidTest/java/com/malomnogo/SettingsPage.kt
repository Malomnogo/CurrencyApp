package com.malomnogo

import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.malomnogo.presentation.core.views.CustomButton
import org.hamcrest.CoreMatchers.allOf
import ru.easycode.presentation.R

class SettingsPage {

    fun checkVisible() {
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(ConstraintLayout::class.java)
            )
        ).check(matches(isDisplayed()))
    }

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.rootLayout),
                isAssignableFrom(ConstraintLayout::class.java)
            )
        ).check(doesNotExist())
    }

    private val fromCurrencyRecyclerView: Int = R.id.fromRecyclerView

    fun checkFromCurrencies(vararg currencies: String) = currencies.forEachIndexed { i, currency ->
        onView(
            RecyclerViewMatcher(
                position = i,
                targetViewId = R.id.currencyTextView,
                recyclerViewId = fromCurrencyRecyclerView
            )
        ).check(matches(withText(currency)))
    }

    fun chooseFrom(position: Int) {
        onView(
            RecyclerViewMatcher(position = position, recyclerViewId = fromCurrencyRecyclerView)
        ).perform(click())
    }

    fun checkChosenFrom(position: Int) {
        onView(
            RecyclerViewMatcher(
                position = position,
                targetViewId = R.id.selectedImageView,
                recyclerViewId = fromCurrencyRecyclerView
            )
        )
//            .check(matches(DrawableMatcher(R.drawable.check_circle24px)))
            .check(matches(isDisplayed()))
    }

    private val toCurrencyRecyclerView: Int = R.id.toRecyclerView

    fun checkToCurrencies(vararg currencies: String) {
        currencies.forEachIndexed { i, currency ->
            onView(
                RecyclerViewMatcher(
                    position = i,
                    targetViewId = R.id.currencyTextView,
                    recyclerViewId = toCurrencyRecyclerView
                )
            )
//                .check(matches(withText(currency)))
        }
    }

    fun chooseTo(position: Int) {
        onView(
            RecyclerViewMatcher(position = position, recyclerViewId = toCurrencyRecyclerView)
        ).perform(click())
    }

    fun checkChosenTo(position: Int) {
        onView(
            RecyclerViewMatcher(
                position = position,
                targetViewId = R.id.selectedImageView,
                recyclerViewId = toCurrencyRecyclerView
            )
        )
//            .check(matches(DrawableMatcher(R.drawable.check_circle24px)))
            .check(matches(isDisplayed()))
    }

    fun checkNoMoreCurrencies() {
        RecyclerViewMatcher(
            position = 0,
            targetViewId = R.id.noMoreTextView,
            recyclerViewId = toCurrencyRecyclerView
        ).matches(withText("No more currencies"))
    }

    fun clickSave() {
        onView(
            allOf(
                withId(R.id.saveButton),
                isAssignableFrom(CustomButton::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        ).perform(click())
    }

    fun clickBack() {
        onView(
            allOf(
                withId(R.id.backButton),
                isAssignableFrom(ImageButton::class.java),
                withParent(withId(R.id.rootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        )
//            .check(matches(DrawableMatcher(R.drawable.arrow_back48px)))
            .perform(click())
    }
}