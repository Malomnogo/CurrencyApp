package com.malomnogo

import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.malomnogo.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun scenarioTest() {
        val loadingPage = LoadingPage()
        loadingPage.checkVisible()
        loadingPage.checkError(message = "No internet connection")
        activityScenarioRule.scenario.recreate()
        loadingPage.checkError(message = "No internet connection")
        loadingPage.clickRetry()
        loadingPage.checkNotVisible()

        val dashboardPage = DashboardPage()
        dashboardPage.checkVisible()
        dashboardPage.checkEmpty()
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkEmpty()
        dashboardPage.goToSettings()
        dashboardPage.checkNotVisible()

        val settingsPage = SettingsPage()
        settingsPage.checkVisible()
        settingsPage.checkFromCurrencies("USD", "EUR", "JPY")

        Espresso.pressBack()
        settingsPage.checkNotVisible()
        dashboardPage.checkEmpty()
        dashboardPage.goToSettings()

        settingsPage.clickBack()
        settingsPage.checkNotVisible()
        dashboardPage.checkEmpty()
        dashboardPage.goToSettings()

        settingsPage.checkVisible()
        settingsPage.checkFromCurrencies("USD", "EUR", "JPY")
        settingsPage.chooseFrom(position = 0)
        settingsPage.checkChosenFrom(position = 0)

        settingsPage.checkToCurrencies("EUR", "JPY")
        activityScenarioRule.scenario.recreate()
        settingsPage.checkChosenFrom(position = 0)
        settingsPage.checkToCurrencies("EUR", "JPY")

        settingsPage.chooseTo(position = 0)
        settingsPage.checkChosenTo(position = 0)
        activityScenarioRule.scenario.recreate()
        settingsPage.checkChosenFrom(position = 0)
        settingsPage.checkToCurrencies("EUR", "JPY")
        settingsPage.checkChosenTo(position = 0)

        settingsPage.clickSave()
        settingsPage.checkNotVisible()

        dashboardPage.checkVisible()
        dashboardPage.checkPair(position = 0, currencyPair = "USD/EUR", rates = "123.45")
        dashboardPage.goToSettings()
        dashboardPage.checkNotVisible()

        settingsPage.checkVisible()
        settingsPage.checkFromCurrencies("USD", "EUR", "JPY")
        settingsPage.chooseFrom(position = 0)
        settingsPage.checkChosenFrom(position = 0)
        settingsPage.checkToCurrencies("JPY")

        settingsPage.chooseTo(position = 0)
        settingsPage.checkChosenTo(position = 0)
        settingsPage.clickSave()

        val premiumPage = PremiumPage()
        premiumPage.checkVisible()
        premiumPage.clickNo()

        settingsPage.checkVisible()
        settingsPage.checkChosenFrom(position = 0)
        settingsPage.checkChosenTo(position = 0)
        settingsPage.clickSave()

        premiumPage.checkVisible()
        premiumPage.clickBuy()
        premiumPage.checkError()

        premiumPage.clickBuy()

        settingsPage.checkVisible()
        settingsPage.checkChosenFrom(position = 0)
        settingsPage.checkChosenTo(position = 0)
        settingsPage.clickSave()

        dashboardPage.checkVisible()
        dashboardPage.checkPair(position = 0, currencyPair = "USD/EUR", rates = "123.45")
        dashboardPage.checkPair(position = 1, currencyPair = "USD/JPY", rates = "123.45")
        dashboardPage.goToSettings()
        dashboardPage.checkNotVisible()

        settingsPage.checkVisible()
        settingsPage.checkFromCurrencies("USD", "EUR", "JPY")
        settingsPage.chooseFrom(position = 0)
        settingsPage.checkChosenFrom(position = 0)
        settingsPage.checkNoMoreCurrencies()
        activityScenarioRule.scenario.recreate()
        settingsPage.checkChosenFrom(position = 0)
        settingsPage.checkFromCurrencies("USD", "EUR", "JPY")
        settingsPage.checkNoMoreCurrencies()

        Espresso.pressBack()
        settingsPage.checkNotVisible()
        dashboardPage.checkPair(position = 0, currencyPair = "USD/EUR", rates = "123.45")
        dashboardPage.checkPair(position = 1, currencyPair = "USD/JPY", rates = "123.45")
        dashboardPage.goToSettings()

        settingsPage.chooseFrom(position = 1)
        settingsPage.checkChosenFrom(position = 1)
        settingsPage.checkToCurrencies("USD", "JPY")

        settingsPage.clickBack()
        settingsPage.checkNotVisible()
        dashboardPage.checkPair(position = 0, currencyPair = "USD/EUR", rates = "123.45")
        dashboardPage.checkPair(position = 1, currencyPair = "USD/JPY", rates = "123.45")

        dashboardPage.remove(position = 0)
        dashboardPage.checkPair(position = 0, currencyPair = "USD/JPY", rates = "123.45")
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkPair(position = 0, currencyPair = "USD/JPY", rates = "123.45")

        dashboardPage.goToSettings()
        settingsPage.chooseFrom(0)
        settingsPage.checkChosenFrom(0)
        settingsPage.chooseTo(0)
        settingsPage.checkChosenTo(0)
        settingsPage.clickSave()
        settingsPage.checkNotVisible()

        dashboardPage.checkPair(position = 0, currencyPair = "USD/JPY", rates = "123.45")
        dashboardPage.checkPair(position = 1, currencyPair = "USD/EUR", rates = "123.45")
        dashboardPage.remove(position = 0)

        dashboardPage.checkPair(position = 0, currencyPair = "USD/EUR", rates = "123.45")
        dashboardPage.remove(position = 0)

        dashboardPage.checkEmpty()
        activityScenarioRule.scenario.recreate()
        dashboardPage.checkEmpty()
    }
}