package com.malomnogo.modules

import com.malomnogo.presentation.core.CustomViewModel

interface Module<T : CustomViewModel> {

    fun viewModel(): T
}