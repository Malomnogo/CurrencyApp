package com.malomnogo.modules

import com.malomnogo.Core
import com.malomnogo.ProvideInstance
import com.malomnogo.data.BasePremiumStorage
import com.malomnogo.presentation.main.Clear
import com.malomnogo.presentation.premium.PremiumObservable
import com.malomnogo.presentation.premium.PremiumViewModel

class PremiumModule(
    private val core: Core,
    private val clear: Clear,
    private val provideInstance: ProvideInstance
) : Module<PremiumViewModel> {

    override fun viewModel() = PremiumViewModel(
        repository =
        provideInstance.providePremiumRepository(
            maxPairs = provideInstance.provideMaxPairs(),
            premiumStorage = BasePremiumStorage(core.provideLocalStorage()),
            provideResources = core.provideResources(),
        ),
        observable = PremiumObservable.Base(),
        navigation = core.provideNavigation(),
        clear = clear,
        runAsync = core.provideRunAsync()
    )
}