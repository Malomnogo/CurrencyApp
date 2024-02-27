package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.presentation.main.MainViewModel

class MainModule(private val core: Core) : Module<MainViewModel> {

    override fun viewModel() = MainViewModel(navigation = core.provideNavigation())
}