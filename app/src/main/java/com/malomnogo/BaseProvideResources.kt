package com.malomnogo

import android.content.Context
import com.malomnogo.currencyapp.R
import com.malomnogo.data.core.ProvideResources
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BaseProvideResources @Inject constructor(
    @ApplicationContext private val context: Context
) : ProvideResources {

    override fun noInternetConnectionMessage() =
        context.resources.getString(R.string.no_internet_connection)

    override fun serviceUnavailableMessage() =
        context.resources.getString(R.string.service_unavailable)

    override fun failPurchaseMessage() =
        context.resources.getString(R.string.fail_purchased)

    override fun maxPairsDescription(maxPairs: Int) =
        String.format(context.resources.getString(R.string.buy_premium_description), maxPairs)

    override fun successPurchaseMessage() = context.resources.getString(R.string.success_purchased)
}