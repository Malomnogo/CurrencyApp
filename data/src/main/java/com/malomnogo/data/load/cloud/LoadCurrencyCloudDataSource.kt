package com.malomnogo.data.load.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface LoadCurrencyCloudDataSource {

    suspend fun currencies(): HashMap<String, String>

    class Base(private val currencyService: CurrencyService) : LoadCurrencyCloudDataSource {
        constructor() : this(
            Retrofit.Builder().baseUrl("https://api.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        })
                        .build()
                )
                .build()
                .create(CurrencyService::class.java)
        )

        override suspend fun currencies() = currencyService.currencies().execute().body()!!
    }
}