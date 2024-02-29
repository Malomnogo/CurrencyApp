package com.malomnogo

import android.content.Context
import androidx.room.Room
import com.malomnogo.data.ProvideResources
import com.malomnogo.data.core.CurrenciesDatabase
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.main.Navigation
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Core {

    fun provideNavigation(): Navigation

    fun provideResources(): ProvideResources

    fun provideRunAsync(): RunAsync

    fun provideDb(): CurrenciesDatabase

    fun provideRetrofit(): Retrofit

    class Base(context: Context) : Core {

        private val navigation by lazy { Navigation.Base() }

        private val provideResources by lazy { BaseProvideResources(context = context) }

        private val runAsync by lazy { RunAsync.Base() }

        private val db by lazy {
            Room.databaseBuilder(
                context,
                CurrenciesDatabase::class.java,
                "currencies_db"
            ).build()
        }

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://www.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }).build()
                ).build()
        }

        override fun provideDb() = db

        override fun provideRetrofit(): Retrofit = retrofit

        override fun provideNavigation() = navigation

        override fun provideResources() = provideResources

        override fun provideRunAsync() = runAsync
    }
}