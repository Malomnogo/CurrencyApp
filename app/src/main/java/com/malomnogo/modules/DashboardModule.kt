package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.ProvideInstance
import com.malomnogo.data.core.HandleError
import com.malomnogo.data.dashboard.CurrencyPairRatesDataSource
import com.malomnogo.data.dashboard.CurrentTimeInMillis
import com.malomnogo.data.dashboard.UpdatedRateDataSource
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.dashboard.cloud.CurrencyRateCloudDataSource
import com.malomnogo.presentation.dashboard.DashboardUiObservable
import com.malomnogo.presentation.dashboard.DashboardViewModel
import com.malomnogo.presentation.main.Clear

class DashboardModule(
    private val core: Core,
    private val clear: Clear,
    private val provideInstance: ProvideInstance
) : Module<DashboardViewModel> {

    override fun viewModel(): DashboardViewModel {
        val cacheDataSource =
            CurrencyPairCacheDataSource.Base(core.provideDb().latestCurrencyDao())
        val currentTimeInMillis = CurrentTimeInMillis.Base()

        return DashboardViewModel(
            navigation = core.provideNavigation(),
            observable = DashboardUiObservable.Base(),
            repository = provideInstance.provideDashboardRepository(
                currencyPairCacheDataSource = cacheDataSource,
                currencyPairRatesDataSource = CurrencyPairRatesDataSource.Base(
                    currentTimeInMillis = currentTimeInMillis,
                    updatedRateDataSource = UpdatedRateDataSource.Base(
                        currentTimeInMillis = currentTimeInMillis,
                        cloudDataSource = CurrencyRateCloudDataSource.Base(
                            retrofit = core.provideRetrofit()
                        ),
                        cacheDataSource = cacheDataSource
                    )
                ),
                handleError = HandleError.Base(core.provideResources())
            ),
            clear = clear,
            runAsync = core.provideRunAsync()
        )
    }
}