package com.malomnogo.data.core

class FakeProvideResources : ProvideResources {

    override fun noInternetConnectionMessage() = "No internet connection"

    override fun serviceUnavailableMessage() = "Service unavailable"

    override fun maxPairsDescription(maxPairs: Int) = "1"

    override fun successPurchaseMessage() = "Success"

    override fun failPurchaseMessage() = "Fail"
}