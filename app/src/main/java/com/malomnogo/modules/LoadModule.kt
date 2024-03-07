package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.ProvideInstance
import com.malomnogo.data.load.cache.CurrenciesCacheDataSource
import com.malomnogo.data.load.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.presentation.load.LoadUiObservable
import com.malomnogo.presentation.load.LoadViewModel
import com.malomnogo.presentation.main.Clear

class LoadModule(
    private val core: Core,
    private val clear: Clear,
    private val provideInstance: ProvideInstance
) : Module<LoadViewModel> {

    override fun viewModel() = LoadViewModel(
        repository = provideInstance.provideLoadCurrenciesRepository(
            cacheDataSource = CurrenciesCacheDataSource.Base(core.provideDb().currenciesDao()),
            cloudDataSource = LoadCurrenciesCloudDataSource.Base(
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