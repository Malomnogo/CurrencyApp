package com.malomnogo.presentation.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {

    private var cachedUiBlock: (Any) -> Unit = {}
    private var cachedResult: Any = ""

    fun returnResult() {
        cachedUiBlock.invoke(cachedResult)
    }

    override fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiBlock: (T) -> Unit,
    ) = runBlocking {
        val result = background.invoke()
        cachedResult = result
        cachedUiBlock = uiBlock as (Any) -> Unit
    }
}