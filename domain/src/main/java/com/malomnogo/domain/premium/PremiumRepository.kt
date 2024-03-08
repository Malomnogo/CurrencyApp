package com.malomnogo.domain.premium

interface PremiumRepository {

    suspend fun buy(): BuyPremiumResult

    fun description(): String
}