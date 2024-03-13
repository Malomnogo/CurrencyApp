package com.malomnogo.modules

import com.malomnogo.data.dashboard.cloud.CurrencyRateService
import com.malomnogo.data.load.cloud.CurrencyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.frankfurter.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }).build()
            ).build()

    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService =
        retrofit.create(CurrencyService::class.java)

    @Provides
    fun provideCurrencyRateService(retrofit: Retrofit): CurrencyRateService =
        retrofit.create(CurrencyRateService::class.java)
}