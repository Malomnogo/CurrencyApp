package com.malomnogo

import com.malomnogo.modules.ProvideModule
import com.malomnogo.presentation.core.CustomViewModel
import com.malomnogo.presentation.core.ProvideViewModel

class BaseProvideViewModel(private val provideModule: ProvideModule) : ProvideViewModel {

    override fun <T : CustomViewModel> viewModel(clazz: Class<T>): T {
        return provideModule.module(clazz).viewModel()
    }
}