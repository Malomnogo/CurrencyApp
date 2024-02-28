package com.malomnogo.data.load.cloud

import retrofit2.Retrofit

interface LoadCurrencyCloudDataSource {

    suspend fun currencies(): HashMap<String, String>

    class Base(private val currencyService: CurrencyService) : LoadCurrencyCloudDataSource {
        constructor(retrofit: Retrofit) : this(
            retrofit.create(CurrencyService::class.java)
        )

        override suspend fun currencies() = currencyService.currencies().execute().body()!!
    }
}