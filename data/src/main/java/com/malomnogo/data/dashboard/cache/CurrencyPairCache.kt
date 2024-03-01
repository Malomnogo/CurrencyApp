package com.malomnogo.data.dashboard.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.malomnogo.data.dashboard.CurrentTimeInMillis
import java.util.concurrent.TimeUnit

@Entity(tableName = "currency_table", primaryKeys = ["from", "to"])
data class CurrencyPairCache(
    @ColumnInfo("from")
    val from: String,
    @ColumnInfo("to")
    val to: String,
    @ColumnInfo("rate")
    val rate: Double = -1.0,
    @ColumnInfo("lastUpdate")
    val lastUpdate: Long = 0L
) {
    fun isNotFresh(currentTimeInMillis: CurrentTimeInMillis) = rate == -1.0 ||
            (currentTimeInMillis.time() - lastUpdate) >= TimeUnit.HOURS.toMillis(24)
}