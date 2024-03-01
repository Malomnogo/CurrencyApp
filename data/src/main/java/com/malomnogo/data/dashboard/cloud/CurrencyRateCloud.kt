package com.malomnogo.data.dashboard.cloud

import com.google.gson.annotations.SerializedName

data class CurrencyRateCloud(
    @SerializedName("rates")
    private val rates: HashMap<String, Double>
) {

    fun rate(currency: String): Double = rates.getValue(currency)
}