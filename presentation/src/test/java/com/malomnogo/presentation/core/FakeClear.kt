package com.malomnogo.presentation.core

import com.malomnogo.presentation.main.Clear
import org.junit.Assert.assertEquals

class FakeClear : Clear {

    private var actual: Class<out CustomViewModel> = FakeViewModel::class.java

    override fun clear(clazz: Class<out CustomViewModel>) {
        actual = clazz
    }

    fun checkCalled(expected: Class<out CustomViewModel>) {
        assertEquals(expected, actual)
    }
}

object FakeViewModel : CustomViewModel