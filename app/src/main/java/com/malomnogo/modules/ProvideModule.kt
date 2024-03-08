package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.ProvideInstance
import com.malomnogo.presentation.core.CustomViewModel
import com.malomnogo.presentation.dashboard.DashboardViewModel
import com.malomnogo.presentation.load.LoadViewModel
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.MainViewModel
import com.malomnogo.presentation.premium.PremiumViewModel
import com.malomnogo.presentation.settings.SettingsViewModel

interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val provideInstance: ProvideInstance,
        private val clear: Clear,
        private val core: Core
    ) : ProvideModule {

        @Suppress("UNCHECKED_CAST")
        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainViewModel::class.java -> MainModule(core)
                LoadViewModel::class.java -> LoadModule(core, clear, provideInstance)
                DashboardViewModel::class.java -> DashboardModule(core, clear, provideInstance)
                SettingsViewModel::class.java -> SettingsModule(core, clear, provideInstance)
                PremiumViewModel::class.java -> PremiumModule(core, clear, provideInstance)
                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }
}