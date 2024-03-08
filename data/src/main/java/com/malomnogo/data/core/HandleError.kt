package com.malomnogo.data.core

import java.net.UnknownHostException

interface HandleError {

    fun handleError(exception: Exception): String

    class Base(private val provideResources: ProvideResources) : HandleError {

        override fun handleError(exception: Exception) = with(provideResources) {
            if (exception is UnknownHostException)
                noInternetConnectionMessage()
            else
                serviceUnavailableMessage()
        }
    }
}