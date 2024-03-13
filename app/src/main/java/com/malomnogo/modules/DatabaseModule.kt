package com.malomnogo.modules

import android.content.Context
import androidx.room.Room
import com.malomnogo.data.core.CurrenciesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CurrenciesDatabase =
        Room.databaseBuilder(context, CurrenciesDatabase::class.java, "currencies_db").build()

    @Provides
    fun provideCurrenciesDao(database: CurrenciesDatabase) = database.currenciesDao()

    @Provides
    fun provideLatestCurrencyDao(database: CurrenciesDatabase) = database.latestCurrencyDao()
}