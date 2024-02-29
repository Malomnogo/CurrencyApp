package com.malomnogo.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Test

class RateFormatTest {

    @Test
    fun test() {
        val rateFormat = RateFormat.Base()
        val expected = "1,23"
        val actual = rateFormat.format(1.23456)
        assertEquals(expected, actual)
    }
}