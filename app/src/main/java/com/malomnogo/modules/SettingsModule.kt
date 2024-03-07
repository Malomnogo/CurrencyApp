package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.ProvideInstance
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.load.cache.CurrenciesCacheDataSource
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.settings.SettingsUiObservable
import com.malomnogo.presentation.settings.SettingsViewModel

class SettingsModule(
    private val core: Core,
    private val clear: Clear,
    private val provideInstance: ProvideInstance
) : Module<SettingsViewModel> {

    override fun viewModel() = SettingsViewModel(
        repository = provideInstance.provideSettingsRepository(
            currenciesCacheDataSource = CurrenciesCacheDataSource.Base(
                core.provideDb().currenciesDao()
            ),
            currencyPairRatesDataSource = CurrencyPairCacheDataSource.Base(
                core.provideDb().latestCurrencyDao()
            )
        ),
        navigation = core.provideNavigation(),
        observable = SettingsUiObservable.Base(),
        clear = clear,
        runAsync = core.provideRunAsync()
    )
}