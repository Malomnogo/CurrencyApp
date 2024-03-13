package com.malomnogo.data.currencies.cloud

import javax.inject.Inject

interface LoadCurrenciesCloudDataSource {

    suspend fun currencies(): HashMap<String, String>

    class Base @Inject constructor(
        private val currencyService: CurrencyService
    ) : LoadCurrenciesCloudDataSource {

        override suspend fun currencies() = currencyService.currencies().execute().body()!!
    }
}