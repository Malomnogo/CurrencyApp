package com.malomnogo.presentation.main

import com.malomnogo.presentation.core.FakeNavigation
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var navigation: FakeNavigation

    @Before
    fun setup() {
        navigation = FakeNavigation()
        viewModel = MainViewModel(navigation = navigation)
    }

    @Test
    fun firstRun() {
        viewModel.init(isFirstRun = true)
        navigation.checkNavigateToLoad()
    }

    @Test
    fun notFirstRun() {
        viewModel.init(isFirstRun = false)
        navigation.checkNotCalled()
    }
}