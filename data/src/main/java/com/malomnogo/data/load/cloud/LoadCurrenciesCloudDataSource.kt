package com.malomnogo.data.load.cloud

import retrofit2.Retrofit

interface LoadCurrenciesCloudDataSource {

    suspend fun currencies(): HashMap<String, String>

    class Base(private val currencyService: CurrencyService) : LoadCurrenciesCloudDataSource {
        constructor(retrofit: Retrofit) : this(
            retrofit.create(CurrencyService::class.java)
        )

        override suspend fun currencies() = currencyService.currencies().execute().body()!!
    }
}