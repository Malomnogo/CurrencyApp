package com.malomnogo.data.load.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrenciesDao {

    @Query("select * from currencies_table")
    suspend fun currencies(): List<CurrencyCache>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currencies: List<CurrencyCache>)
}
