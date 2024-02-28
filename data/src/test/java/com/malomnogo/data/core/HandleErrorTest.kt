package com.malomnogo.data.core

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class HandleErrorTest {

    private lateinit var handleError: HandleError
    private lateinit var provideResources: FakeProvideResources

    @Before
    fun setup() {
        provideResources = FakeProvideResources()
        handleError = HandleError.Base(provideResources)
    }

    @Test
    fun testUnknownHostException() {
        val actual = handleError.handleError(UnknownHostException())
        val expected = "No internet connection"
        assertEquals(expected, actual)
    }

    @Test
    fun testIllegalStateException() {
        val actual = handleError.handleError(IllegalStateException())
        val expected = "Service unavailable"
        assertEquals(expected, actual)
    }
}