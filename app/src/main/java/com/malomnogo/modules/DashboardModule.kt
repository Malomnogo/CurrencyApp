package com.malomnogo.modules

import com.malomnogo.ProvideInstance
import com.malomnogo.data.core.HandleError
import com.malomnogo.data.currencies.cache.CurrenciesCacheDataSource
import com.malomnogo.data.currencies.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.data.dashboard.CurrencyPairRatesDataSource
import com.malomnogo.data.dashboard.CurrentTimeInMillis
import com.malomnogo.data.dashboard.UpdatedRateDataSource
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.data.dashboard.cloud.CurrencyRateCloudDataSource
import com.malomnogo.domain.dashboard.DashboardItem
import com.malomnogo.domain.dashboard.DashboardRepository
import com.malomnogo.domain.dashboard.DashboardResult
import com.malomnogo.presentation.dashboard.BaseDashboardItemMapper
import com.malomnogo.presentation.dashboard.BaseDashboardResultMapper
import com.malomnogo.presentation.dashboard.DashboardUi
import com.malomnogo.presentation.dashboard.DashboardUiObservable
import com.malomnogo.presentation.dashboard.DashboardUiState
import com.malomnogo.presentation.dashboard.Delimiter
import com.malomnogo.presentation.dashboard.RateFormat
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
abstract class DashboardModule {

    @Binds
    @ViewModelScoped
    abstract fun bindObservable(
        dashboardObservable: DashboardUiObservable.Base
    ): DashboardUiObservable

    @Binds
    abstract fun bindLoadCurrenciesCloudDataSource(
        cloudDataSource: LoadCurrenciesCloudDataSource.Base
    ): LoadCurrenciesCloudDataSource

    @Binds
    abstract fun bindCurrencyRateCloudDataSource(
        currenciesCloudDataSource: CurrencyRateCloudDataSource.Base
    ): CurrencyRateCloudDataSource

    @Binds
    abstract fun bindCurrenciesCacheDataSource(
        cacheDataSource: CurrenciesCacheDataSource.Base
    ): CurrenciesCacheDataSource.Mutable

    @Binds
    abstract fun bindCurrencyPairCacheDataSource(
        cacheDataSource: CurrencyPairCacheDataSource.Base
    ): CurrencyPairCacheDataSource.Save

    @Binds
    abstract fun bindCurrencyPairRatesDataSource(
        pairRatesDataSource: CurrencyPairRatesDataSource.Base
    ): CurrencyPairRatesDataSource

    @Binds
    abstract fun bindUpdatedRateDataSource(
        updatedRateDataSource: UpdatedRateDataSource.Base
    ): UpdatedRateDataSource



    @Binds
    abstract fun bindCreateDelimiter(
        delimiter: Delimiter.Base
    ): Delimiter.Create

    @Binds
    abstract fun bindRemoveDelimiter(
        delimiter: Delimiter.Base
    ): Delimiter.Split

    @Binds
    abstract fun bindMapper(
        mapper: BaseDashboardResultMapper
    ): DashboardResult.Mapper<DashboardUiState>

    @Binds
    abstract fun bindDashboardItemMapper(
        mapper: BaseDashboardItemMapper
    ): DashboardItem.Mapper<DashboardUi>

    companion object {
        @Provides
        fun provideDelimiter(): Delimiter.Base = Delimiter.Base()

        @Provides
        fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        fun provideCurrentTimeInMillis(): CurrentTimeInMillis = CurrentTimeInMillis.Base()

        @Provides
        fun provideRatesFormatter(): RateFormat = RateFormat.Base()

        @Provides
        fun provideRepository(
            provideInstance: ProvideInstance,
            cacheDataSource: CurrencyPairCacheDataSource.Base,
            currencyPairRatesDataSource: CurrencyPairRatesDataSource.Base,
            cloudDataSource: LoadCurrenciesCloudDataSource,
            allCurrenciesCacheDataSource: CurrenciesCacheDataSource.Mutable,
            handleError: HandleError.Base
        ): DashboardRepository = provideInstance.provideDashboardRepository(
            currencyPairCacheDataSource = cacheDataSource,
            currencyPairRatesDataSource = currencyPairRatesDataSource,
            cloudDataSource = cloudDataSource,
            cacheDataSource = allCurrenciesCacheDataSource,
            handleError = handleError
        )
    }
}