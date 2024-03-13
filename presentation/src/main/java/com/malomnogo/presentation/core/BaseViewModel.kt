package com.malomnogo.presentation.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseViewModel(private val runAsync: RunAsync) : ViewModel() {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    protected fun <T : Any> runAsync(
        background: suspend () -> T,
        uiBlock: (T) -> Unit
    ) {
        runAsync.start(viewModelScope, background, uiBlock)
    }
}

interface RunAsync {

    fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiBlock: (T) -> Unit
    )

    class Base @Inject constructor() : RunAsync {

        override fun <T : Any> start(
            coroutineScope: CoroutineScope,
            background: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            coroutineScope.launch(Dispatchers.IO) {
                val result = background.invoke()
                withContext(Dispatchers.Main) {
                    uiBlock.invoke(result)
                }
            }
        }
    }
}