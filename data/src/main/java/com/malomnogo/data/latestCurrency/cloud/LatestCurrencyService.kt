package com.malomnogo.data.latestCurrency.cloud

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestCurrencyService {

    @GET("latest")
    fun latestCurrency(
        @Query("from") from: String,
        @Query("to") to: String
    ): Call<LatestCurrencyCloud>
}