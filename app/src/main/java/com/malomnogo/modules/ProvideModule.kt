package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.presentation.core.CustomViewModel
import com.malomnogo.presentation.dashboard.DashboardViewModel
import com.malomnogo.presentation.load.LoadViewModel
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.main.MainViewModel

interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val clear: Clear,
        private val core: Core
    ) : ProvideModule {

        @Suppress("UNCHECKED_CAST")
        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainViewModel::class.java -> MainModule(core)
                LoadViewModel::class.java -> LoadModule(core, clear)
                DashboardViewModel::class.java -> DashboardModule(core, clear)
                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }
}