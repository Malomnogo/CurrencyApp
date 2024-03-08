package com.malomnogo.presentation.settings

import com.malomnogo.domain.premium.PremiumInteractor
import com.malomnogo.domain.premium.SaveResult
import com.malomnogo.presentation.core.BaseViewModel
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.core.UpdateUi
import com.malomnogo.presentation.dashboard.DashboardScreen
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.Navigation

class SettingsViewModel(
    private val interactor: PremiumInteractor,
    private val navigation: Navigation,
    private val observable: SettingsUiObservable,
    private val clear: Clear,
    runAsync: RunAsync,
    private val chooseMapper: ChooseMapper = ChooseMapper.Base(),
    private val saveMapper: SaveResult.Mapper = BaseSaveResultMapper(navigation, clear)
) : BaseViewModel(runAsync) {

    fun init(bundleWrapper: BundleWrapper.Mutable) {
        if (bundleWrapper.isEmpty())
            runAsync({
                interactor.currencies()
            }) {
                observable.updateUi(
                    SettingsUiState.Initial(it.map { currency -> CurrencyChoiceUi.Base(currency) })
                )
            }
        else {
            val (from, to) = bundleWrapper.restore()
            choose(from)
            if (to.isNotEmpty())
                chooseDestination(from, to)
        }
    }

    fun choose(from: String) {
        runAsync({
            chooseMapper.map(from, interactor.currencies(), interactor.currenciesDestinations(from))
        }) {
            observable.updateUi(it)
        }
    }

    fun chooseDestination(from: String, to: String) {
        runAsync({
            SettingsUiState.ReadyToSave(
                toList = interactor.currenciesDestinations(from).map {
                    CurrencyChoiceUi.Base(it, it == to)
                }
            )
        }) {
            observable.updateUi(it)
        }
    }

    fun save(from: String, to: String) {
        runAsync({
            interactor.save(from, to)
        }) {
            it.map(saveMapper)
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