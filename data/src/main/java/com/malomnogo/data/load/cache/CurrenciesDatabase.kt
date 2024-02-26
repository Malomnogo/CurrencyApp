package com.malomnogo.data.load.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyCache::class], version = 1, exportSchema = false)
abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun picturesDao(): CurrenciesDao
}
