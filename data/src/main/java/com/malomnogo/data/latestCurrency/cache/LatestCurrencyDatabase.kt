package com.malomnogo.data.latestCurrency.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LatestCurrencyCache::class], version = 1, exportSchema = false)
abstract class LatestCurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): LatestCurrencyDao
}
