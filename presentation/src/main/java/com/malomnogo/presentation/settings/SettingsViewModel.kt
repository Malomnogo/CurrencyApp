package com.malomnogo.presentation.settings

import com.malomnogo.domain.settings.SettingsRepository
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation

class SettingsViewModel(
    private val repository: SettingsRepository,
    private val navigation: Navigation,
    private val observable: SettingsUiObservable,
    private val clear: Clear,
    runAsync: RunAsync,
    private val chooseMapper: ChooseMapper = ChooseMapper.Base()
) : BaseViewModel(runAsync) {

    fun init() {
        runAsync({
            repository.currencies()
        }) {
            observable.updateUi(
                SettingsUiState.Initial(
                    it.map { currency ->
                        CurrencyChoiceUi.Base(currency)
                    }
                )
            )
        }
    }

    fun choose(from: String) {
        runAsync({
            chooseMapper.map(from, repository.currencies(), repository.currenciesDestinations(from))
        }) { observable.updateUi(it) }
    }

    fun chooseDestination(from: String, to: String) {
        runAsync({
            SettingsUiState.ReadyToSave(
                toList = repository.currenciesDestinations(from).map {
                    CurrencyChoiceUi.Base(it, it == to)
                }
            )
        }) {
            observable.updateUi(it)
        }
    }

    fun save(from: String, to: String) {
        runAsync({
            repository.save(from, to)
        }) {
            navigateToDashboard()
        }
    }

    fun navigateToDashboard() {
        navigation.updateUi(DashboardScreen)
        clear.clear(this::class.java)
    }

    fun startGettingUpdates(observer: UpdateUi<SettingsUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }
}

interface ChooseMapper {

    fun map(from: String, fromList: List<String>, toList: List<String>): SettingsUiState

    class Base : ChooseMapper {

        override fun map(
            from: String,
            fromList: List<String>,
            toList: List<String>
        ): SettingsUiState {
            val fromChoices = fromList.map { CurrencyChoiceUi.Base(it, it == from) }
            val toChoices = toList.map { CurrencyChoiceUi.Base(it) }
            return if (toChoices.isEmpty())
                SettingsUiState.Hint(fromChoices)
            else
                SettingsUiState.Destinations(fromChoices, toChoices)
        }
    }
}