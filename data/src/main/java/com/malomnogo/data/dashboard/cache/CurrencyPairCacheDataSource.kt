package com.malomnogo.data.dashboard.cache

interface CurrencyPairCacheDataSource {

    interface Save {

        suspend fun save(currency: CurrencyPairCache)
    }

    interface Read {

        suspend fun read(): List<CurrencyPairCache>
    }

    interface Remove {
        suspend fun remove(from: String, to: String)
    }

    interface Mutate : Save, Remove

    interface Mutable : Mutate, Read

    class Base(private val currencyPairDao: CurrencyPairDao) : Mutable {

        override suspend fun save(currency: CurrencyPairCache) {
            currencyPairDao.insert(currency)
        }

        override suspend fun remove(from: String, to: String) {
            currencyPairDao.favoriteRates().find { it.from == from && it.to == to }?.let {
                currencyPairDao.remove(it)
            }
        }

        override suspend fun read() = currencyPairDao.favoriteRates()
    }
}