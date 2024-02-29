package com.malomnogo.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.malomnogo.data.core.CurrenciesDatabase
import com.malomnogo.data.load.cache.CurrenciesDao
import com.malomnogo.data.load.cache.CurrencyCache
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: CurrenciesDatabase
    private lateinit var dao: CurrenciesDao

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, CurrenciesDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = db.currenciesDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test() = runBlocking {
        assertEquals(emptyList<CurrencyCache>(), dao.currencies())
        val expected = listOf(
            CurrencyCache("USD", "United States Dollar"),
            CurrencyCache("AUD", "Australian Dollar"),
            CurrencyCache("JPY", "Japanese Yen")
        )
        dao.insert(expected)
        assertEquals(expected, dao.currencies())
    }
}