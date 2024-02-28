package com.malomnogo.data.latestCurrency.cloud

import retrofit2.Retrofit

interface LatestCurrencyCloudDataSource {

    fun latestCurrency(from: String, to: String): Double

    class Base(private val latestCurrencyService: LatestCurrencyService) :
        LatestCurrencyCloudDataSource {

        constructor(retrofit: Retrofit) : this(
            retrofit.create(LatestCurrencyService::class.java)
        )

        override fun latestCurrency(from: String, to: String) =
            latestCurrencyService.latestCurrency(from, to).execute().body()!!.rate(to)
    }
}