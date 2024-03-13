package com.malomnogo.modules

import com.malomnogo.ProvideInstance
import com.malomnogo.data.core.HandleError
import com.malomnogo.data.load.cache.CurrenciesCacheDataSource
import com.malomnogo.data.load.cloud.LoadCurrenciesCloudDataSource
import com.malomnogo.domain.load.LoadCurrenciesRepository
import com.malomnogo.domain.load.LoadCurrenciesResult
import com.malomnogo.presentation.load.BaseLoadResultMapper
import com.malomnogo.presentation.load.LoadUiObservable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoadModule {

    @Binds
    @ViewModelScoped
    abstract fun bindObservable(
        observable: LoadUiObservable.Base
    ): LoadUiObservable

    @Binds
    abstract fun bindCloudDataSource(
        cloudDataSource: LoadCurrenciesCloudDataSource.Base
    ): LoadCurrenciesCloudDataSource

    @Binds
    abstract fun bindCacheDataSource(
        cacheDataSource: CurrenciesCacheDataSource.Base
    ): CurrenciesCacheDataSource.Mutable

    @Binds
    abstract fun bindMapper(mapper: BaseLoadResultMapper): LoadCurrenciesResult.Mapper

    companion object {
        @Provides
        fun provideRepository(
            provideInstance: ProvideInstance,
            cloudDataSource: LoadCurrenciesCloudDataSource,
            cacheDataSource: CurrenciesCacheDataSource.Mutable,
            handleError: HandleError.Base
        ): LoadCurrenciesRepository = provideInstance.provideLoadCurrenciesRepository(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            handleError = handleError
        )
    }
}