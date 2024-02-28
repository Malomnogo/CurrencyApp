package com.malomnogo.data.latestCurrency.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LatestCurrencyDao {

    @Query("select * from currency_table")
    suspend fun favoriteRates(): List<LatestCurrencyCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: LatestCurrencyCache)
}