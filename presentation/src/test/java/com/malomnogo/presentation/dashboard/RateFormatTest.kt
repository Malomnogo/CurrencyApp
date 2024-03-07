package com.malomnogo.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Test

class RateFormatTest {

    @Test
    fun test() {
        var rateFormat = RateFormat.Base(format = "%.2f")
        var expected = "1,23"
        var actual = rateFormat.format(1.23456)
        assertEquals(expected, actual)

        rateFormat = RateFormat.Base(format = "%.3f")
        expected = "7,891"
        actual = rateFormat.format(7.8910)
        assertEquals(expected, actual)
    }
}