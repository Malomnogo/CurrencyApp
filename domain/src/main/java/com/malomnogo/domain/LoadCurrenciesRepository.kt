package com.malomnogo.domain

interface LoadCurrenciesRepository {

    suspend fun fetchCurrencies()
}

interface LoadCurrenciesResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(currencies: List<CurrencyCache>)

        fun mapError(message: String)
    }

    data class Success(private val currencies: List<CurrencyCache>) : LoadCurrenciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(currencies)
        }
    }

    data class Error(private val message: String) : LoadCurrenciesResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }
}