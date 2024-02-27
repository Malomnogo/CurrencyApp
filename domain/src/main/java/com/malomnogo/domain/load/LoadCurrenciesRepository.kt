package com.malomnogo.domain.load

interface LoadCurrenciesRepository {

    suspend fun fetchCurrencies(): LoadCurrenciesResult
}