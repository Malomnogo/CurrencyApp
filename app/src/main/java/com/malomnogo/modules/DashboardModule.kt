package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.data.core.HandleError
import com.malomnogo.data.latestCurrency.BaseDashboardRepository
import com.malomnogo.data.latestCurrency.CurrencyPairRatesDataSource
import com.malomnogo.data.latestCurrency.CurrentTimeInMillis
import com.malomnogo.data.latestCurrency.UpdatedRateDataSource
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCacheDataSource
import com.malomnogo.data.latestCurrency.cloud.LatestCurrencyCloudDataSource
import com.malomnogo.presentation.dashboard.DashboardUiObservable
import com.malomnogo.presentation.dashboard.DashboardViewModel
import com.malomnogo.presentation.main.Clear

class DashboardModule(
    private val core: Core,
    private val clear: Clear
) : Module<DashboardViewModel> {

    override fun viewModel(): DashboardViewModel {
        val cacheDataSource =
            LatestCurrencyCacheDataSource.Base(core.provideDb().latestCurrencyDao())
        val currentTimeInMillis = CurrentTimeInMillis.Base()

        return DashboardViewModel(
            navigation = core.provideNavigation(),
            observable = DashboardUiObservable.Base(),
            repository = BaseDashboardRepository(
                cacheDataSource = cacheDataSource,
                currencyPairRatesDataSource = CurrencyPairRatesDataSource.Base(
                    currentTimeInMillis = currentTimeInMillis,
                    updatedRateDataSource = UpdatedRateDataSource.Base(
                        currentTimeInMillis = currentTimeInMillis,
                        cloudDataSource = LatestCurrencyCloudDataSource.Base(
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