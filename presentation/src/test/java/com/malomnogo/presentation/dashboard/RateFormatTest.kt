package com.malomnogo.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DecimalFormat

class RateFormatTest {

    @Test
    fun test() {
        var rateFormat = RateFormat.Base(decimalFormat = DecimalFormat("#.##"))
        var expected = "1,23"
        var actual = rateFormat.format(1.23456)
        assertEquals(expected, actual)

        rateFormat = RateFormat.Base(decimalFormat = DecimalFormat("#.##"))
        expected = "1"
        actual = rateFormat.format(1.0)
        assertEquals(expected, actual)

        rateFormat = RateFormat.Base(decimalFormat = DecimalFormat("#.##"))
        expected = "1,2"
        actual = rateFormat.format(1.20)
        assertEquals(expected, actual)

        rateFormat = RateFormat.Base(decimalFormat = DecimalFormat("#.###"))
        expected = "1,234"
        actual = rateFormat.format(1.23456)
        assertEquals(expected, actual)
    }
}