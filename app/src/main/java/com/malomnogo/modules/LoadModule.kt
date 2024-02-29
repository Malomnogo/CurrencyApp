package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.data.load.BaseLoadCurrencyRepository
import com.malomnogo.data.load.cache.CurrencyCacheDataSource
import com.malomnogo.data.load.cloud.LoadCurrencyCloudDataSource
import com.malomnogo.presentation.load.LoadUiObservable
import com.malomnogo.presentation.load.LoadViewModel
import com.malomnogo.presentation.main.Clear

class LoadModule(private val core: Core, private val clear: Clear) : Module<LoadViewModel> {

    override fun viewModel() = LoadViewModel(
        repository = BaseLoadCurrencyRepository(
            cacheDataSource = CurrencyCacheDataSource.Base(core.provideDb().currenciesDao()),
            cloudDataSource = LoadCurrencyCloudDataSource.Base(
                retrofit = core.provideRetrofit()
            ),
            provideResources = core.provideResources()
        ),
        uiObservable = LoadUiObservable.Base(),
        navigation = core.provideNavigation(),
        clear = clear,
        runAsync = core.provideRunAsync()
    )
}