package com.malomnogo.data.core

import com.malomnogo.data.ProvideResources

class FakeProvideResources : ProvideResources {

    override fun noInternetConnectionMessage() = "No internet connection"

    override fun serviceUnavailableMessage() = "Service unavailable"
}