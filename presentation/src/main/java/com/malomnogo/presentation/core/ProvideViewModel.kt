package com.malomnogo.presentation.core

import com.malomnogo.presentation.main.Clear

interface ProvideViewModel {

    fun <T : CustomViewModel> viewModel(clazz: Class<T>): T

    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel, Clear {

        private val map = HashMap<Class<out CustomViewModel>, CustomViewModel>()

        @Suppress("UNCHECKED_CAST")
        override fun <T : CustomViewModel> viewModel(clazz: Class<T>): T =
            if (map.containsKey(clazz)) {
                map[clazz]
            } else {
                val viewModel = makeViewModel.viewModel(clazz)
                map[clazz] = viewModel
                viewModel
            } as T

        override fun clear(clazz: Class<out CustomViewModel>) {
            map.remove(clazz)
        }
    }
}