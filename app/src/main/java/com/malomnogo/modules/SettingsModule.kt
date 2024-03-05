package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.load.cache.CurrencyCacheDataSource
import com.malomnogo.data.settings.BaseSettingsRepository
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.settings.SettingsUiObservable
import com.malomnogo.presentation.settings.SettingsViewModel

class SettingsModule(private val core: Core, private val clear: Clear) : Module<SettingsViewModel> {

    override fun viewModel() = SettingsViewModel(
        repository = BaseSettingsRepository(
            allCacheDataSource = CurrencyCacheDataSource.Base(
                core.provideDb().currenciesDao()
            ),
            favoriteCacheDataSource = CurrencyPairCacheDataSource.Base(
                core.provideDb().latestCurrencyDao()
            )
        ),
        navigation = core.provideNavigation(),
        observable = SettingsUiObservable.Base(),
        clear = clear,
        runAsync = core.provideRunAsync()
    )
}