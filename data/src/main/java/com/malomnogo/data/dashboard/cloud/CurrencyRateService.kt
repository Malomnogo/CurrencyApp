package com.malomnogo.data.dashboard.cloud

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRateService {

    @GET("latest")
    fun latestCurrency(
        @Query("from") from: String,
        @Query("to") to: String
    ): Call<CurrencyRateCloud>
}