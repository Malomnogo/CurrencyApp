package com.malomnogo

import android.app.Application
import com.malomnogo.modules.ProvideModule
import com.malomnogo.presentation.core.CustomViewModel
import com.malomnogo.presentation.core.ProvideViewModel
import com.malomnogo.presentation.main.Clear

abstract class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val clear = object : Clear {
            override fun clear(clazz: Class<out CustomViewModel>) {
                factory.clear(clazz)
            }

        }
        factory = ProvideViewModel.Factory(
            BaseProvideViewModel(
                ProvideModule.Base(provideInstance(), clear, Core.Base(this))
            )
        )
    }

    abstract fun provideInstance(): ProvideInstance

    override fun <T : CustomViewModel> viewModel(clazz: Class<T>): T = factory.viewModel(clazz)
}

class Release : App() {

    override fun provideInstance() = ProvideInstance.Base()

}

class Mock : App() {

    override fun provideInstance() = ProvideInstance.Mock()
}