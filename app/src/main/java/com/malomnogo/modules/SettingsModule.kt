package com.malomnogo.modules

import com.malomnogo.ProvideInstance
import com.malomnogo.data.currencies.cache.CurrenciesCacheDataSource
import com.malomnogo.data.dashboard.cache.CurrencyPairCacheDataSource
import com.malomnogo.domain.premium.SaveResult
import com.malomnogo.domain.settings.SettingsRepository
import com.malomnogo.presentation.settings.BaseSaveResultMapper
import com.malomnogo.presentation.settings.ChooseMapper
import com.malomnogo.presentation.settings.SettingsUiObservable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindObservable(
        observable: SettingsUiObservable.Base
    ): SettingsUiObservable

    @Binds
    abstract fun bindAllCacheDataSource(
        allCacheDataSource: CurrenciesCacheDataSource.Base
    ): CurrenciesCacheDataSource.Read

    @Binds
    abstract fun bindFavoriteCacheDataSource(
        favoriteCacheDataSource: CurrencyPairCacheDataSource.Base
    ): CurrencyPairCacheDataSource.Mutable

    @Binds
    abstract fun bindSaveMapper(
        mapper: BaseSaveResultMapper
    ): SaveResult.Mapper

    @Binds
    abstract fun bindChooseMapper(
        mapper: ChooseMapper.Base
    ): ChooseMapper

    companion object {
        @Provides
        fun provideRepository(
            provideInstance: ProvideInstance,
            currenciesCacheDataSource: CurrenciesCacheDataSource.Base,
            favoriteCurrenciesCacheDataSource: CurrencyPairCacheDataSource.Base
        ): SettingsRepository = provideInstance.provideSettingsRepository(
            currenciesCacheDataSource,
            favoriteCurrenciesCacheDataSource
        )
    }
}