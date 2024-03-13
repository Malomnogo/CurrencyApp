package com.malomnogo.modules

import com.malomnogo.ProvideInstance
import com.malomnogo.data.BasePremiumStorage
import com.malomnogo.data.core.ProvideResources
import com.malomnogo.domain.premium.BuyPremiumResult
import com.malomnogo.domain.premium.PremiumInteractor
import com.malomnogo.domain.premium.PremiumStorage
import com.malomnogo.domain.settings.SettingsRepository
import com.malomnogo.presentation.premium.BaseBuyPremiumResultMapper
import com.malomnogo.presentation.premium.PremiumObservable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PremiumModule {

    @Binds
    @ViewModelScoped
    abstract fun bindObservable(
        premiumObservable: PremiumObservable.Base
    ): PremiumObservable

    @Binds
    abstract fun bindSavePremiumStorage(
        premiumStorage: BasePremiumStorage
    ): PremiumStorage.Save

    @Binds
    abstract fun bindPremiumInteractor(
        premiumInteractor: PremiumInteractor.Base
    ): PremiumInteractor

    @Binds
    abstract fun bindPremiumResultMapper(
        premiumResultMapper: BaseBuyPremiumResultMapper
    ): BuyPremiumResult.Mapper

    companion object {
        @Provides
        fun provideRepository(
            provideInstance: ProvideInstance,
            premiumStorage: BasePremiumStorage,
            provideResources: ProvideResources
        ) = provideInstance.providePremiumRepository(
            maxPairs = provideInstance.provideMaxPairs(),
            premiumStorage = premiumStorage,
            provideResources = provideResources
        )

        @Provides
        fun provideInteractor(
            provideInstance: ProvideInstance,
            repository: SettingsRepository,
            premiumStorage: BasePremiumStorage
        ) = PremiumInteractor.Base(
            repository = repository,
            premiumStorage = premiumStorage,
            maxPairs = provideInstance.provideMaxPairs()
        )
    }
}