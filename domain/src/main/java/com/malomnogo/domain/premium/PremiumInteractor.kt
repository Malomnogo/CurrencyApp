package com.malomnogo.domain.premium

import com.malomnogo.domain.settings.SettingsRepository
import javax.inject.Inject

interface PremiumInteractor {

    suspend fun currencies(): List<String>

    suspend fun currenciesDestinations(from: String): List<String>

    suspend fun save(from: String, to: String): SaveResult

    class Base @Inject constructor(
        private val repository: SettingsRepository,
        private val premiumStorage: PremiumStorage.Read,
        private val maxPairs: Int
    ) : PremiumInteractor {

        override suspend fun currencies(): List<String> = repository.currencies()

        override suspend fun currenciesDestinations(from: String) =
            repository.currenciesDestinations(from)

        override suspend fun save(from: String, to: String) =
            if (premiumStorage.read() || repository.savedPairsCount() < maxPairs) {
                repository.save(from, to)
                SaveResult.Success
            } else
                SaveResult.NeedPremium
    }
}