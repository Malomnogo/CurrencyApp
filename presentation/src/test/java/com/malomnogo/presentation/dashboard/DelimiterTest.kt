package com.malomnogo.presentation.dashboard

import org.junit.Assert.assertEquals
import org.junit.Test

class DelimiterTest {

    @Test
    fun test() {
        var delimiter = Delimiter.Base(delimiter = "/")
        assertEquals("USD/EUR", delimiter.create(from = "USD", to = "EUR"))
        assertEquals(Pair("USD", "EUR"), delimiter.split("USD/EUR"))

        delimiter = Delimiter.Base(delimiter = "-")
        assertEquals("USD-EUR", delimiter.create(from = "USD", to = "EUR"))
        assertEquals(Pair("USD", "EUR"), delimiter.split("USD-EUR"))
    }
}