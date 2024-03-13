package com.malomnogo.data.core

import java.net.UnknownHostException
import javax.inject.Inject

interface HandleError {

    fun handleError(exception: Exception): String

    class Base @Inject constructor(private val provideResources: ProvideResources) : HandleError {

        override fun handleError(exception: Exception) = with(provideResources) {
            if (exception is UnknownHostException)
                noInternetConnectionMessage()
            else
                serviceUnavailableMessage()
        }
    }
}