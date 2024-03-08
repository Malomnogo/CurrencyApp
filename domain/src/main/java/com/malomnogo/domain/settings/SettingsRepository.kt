package com.malomnogo.domain.settings

interface SettingsRepository {

    suspend fun currencies(): List<String>

    suspend fun currenciesDestinations(from: String): List<String>

    suspend fun save(from: String, to: String)

    suspend fun savedPairsCount(): Int
}