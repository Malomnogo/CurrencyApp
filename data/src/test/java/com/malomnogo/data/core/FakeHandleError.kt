package com.malomnogo.data.core

import java.net.UnknownHostException

class FakeHandleError(private val fakeProvideResources: FakeProvideResources) : HandleError {

    override fun handleError(exception: Exception) = with(fakeProvideResources) {
        if (exception is UnknownHostException)
            noInternetConnectionMessage()
        else
            serviceUnavailableMessage()
    }
}