package com.malomnogo.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.malomnogo.data.currencies.cache.CurrenciesDao
import com.malomnogo.data.currencies.cache.CurrencyCache
import com.malomnogo.data.dashboard.cache.CurrencyPairCache
import com.malomnogo.data.dashboard.cache.CurrencyPairDao

@Database(
    entities = [CurrencyCache::class, CurrencyPairCache::class],
    version = 1,
    exportSchema = false
)
abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao

    abstract fun latestCurrencyDao(): CurrencyPairDao
}
