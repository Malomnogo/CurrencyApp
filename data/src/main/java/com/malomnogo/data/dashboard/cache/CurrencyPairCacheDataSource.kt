package com.malomnogo.data.dashboard.cache

interface CurrencyPairCacheDataSource {

    interface Save {

        suspend fun save(currency: CurrencyPairCache)
    }

    interface Read {

        suspend fun read(): List<CurrencyPairCache>
    }

    interface Mutable : Save, Read

    class Base(private val currencyPairDao: CurrencyPairDao) : Mutable {

        override suspend fun save(currency: CurrencyPairCache) {
            currencyPairDao.insert(currency)
        }

        override suspend fun read() = currencyPairDao.favoriteRates()
    }
}