package com.malomnogo.modules

import com.malomnogo.BaseLocalStorage
import com.malomnogo.BaseProvideResources
import com.malomnogo.ProvideInstance
import com.malomnogo.data.core.HandleError
import com.malomnogo.data.core.ProvideResources
import com.malomnogo.domain.LocalStorage
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.main.Navigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindProvideInstances(provideInstance: ProvideInstance.Mock): ProvideInstance

    @Binds
    @Singleton
    abstract fun bindRunAsync(runAsync: RunAsync.Base): RunAsync

    @Binds
    @Singleton
    abstract fun bindProvideResources(provideResources: BaseProvideResources): ProvideResources

    @Binds
    @Singleton
    abstract fun bindHandleError(handleError: HandleError.Base): HandleError

    @Binds
    @Singleton
    abstract fun bindNavigation(navigation: Navigation.Base): Navigation

    @Binds
    @Singleton
    abstract fun bindsLocalStorage(localStorage: BaseLocalStorage): LocalStorage.Mutable
}