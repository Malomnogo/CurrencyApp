package com.malomnogo.data.dashboard.cloud

import retrofit2.Retrofit

interface CurrencyRateCloudDataSource {

    fun latestCurrency(from: String, to: String): Double

    class Base(private val currencyRateService: CurrencyRateService) : CurrencyRateCloudDataSource {

        constructor(retrofit: Retrofit) : this(retrofit.create(CurrencyRateService::class.java))

        override fun latestCurrency(from: String, to: String) =
            currencyRateService.latestCurrency(from, to).execute().body()!!.rate(to)
    }
}