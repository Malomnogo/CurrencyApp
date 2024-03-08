package com.malomnogo.data.core

interface ProvideResources {

    fun noInternetConnectionMessage(): String

    fun serviceUnavailableMessage(): String

    fun maxPairsDescription(maxPairs: Int): String

    fun successPurchaseMessage(): String

    fun failPurchaseMessage(): String
}