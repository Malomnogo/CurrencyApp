package com.malomnogo.data.latestCurrency.cache

interface LatestCurrencyCacheDataSource {

    interface Save {

        suspend fun save(currency: LatestCurrencyCache)
    }

    interface Read {

        suspend fun read(): List<LatestCurrencyCache>
    }

    interface Mutable : Save, Read

    class Base(private val latestCurrencyDao: LatestCurrencyDao) : Mutable {

        override suspend fun save(currency: LatestCurrencyCache) {
            latestCurrencyDao.insert(currency)
        }

        override suspend fun read() = latestCurrencyDao.favoriteRates()
    }
}