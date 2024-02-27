package com.malomnogo

import android.content.Context
import androidx.room.Room
import com.malomnogo.data.ProvideResources
import com.malomnogo.data.load.cache.CurrenciesDatabase
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.main.Navigation

interface Core {

    fun provideNavigation(): Navigation

    fun provideResources(): ProvideResources

    fun provideRunAsync(): RunAsync

    fun provideDb(): CurrenciesDatabase

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

        override fun provideDb() = db

        override fun provideNavigation() = navigation

        override fun provideResources() = provideResources

        override fun provideRunAsync() = runAsync
    }
}