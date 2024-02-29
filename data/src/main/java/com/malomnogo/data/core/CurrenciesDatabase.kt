package com.malomnogo.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyCache
import com.malomnogo.data.latestCurrency.cache.LatestCurrencyDao
import com.malomnogo.data.load.cache.CurrenciesDao
import com.malomnogo.data.load.cache.CurrencyCache

@Database(
    entities = [CurrencyCache::class, LatestCurrencyCache::class],
    version = 1,
    exportSchema = false
)
abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao

    abstract fun latestCurrencyDao(): LatestCurrencyDao
}
