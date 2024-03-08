package com.malomnogo.data

import com.malomnogo.domain.LocalStorage
import com.malomnogo.domain.premium.PremiumStorage

class BasePremiumStorage(private val localStorage: LocalStorage.Mutable) : PremiumStorage.Mutable {

    override fun read() = localStorage.read(KEY, false)

    override fun save() {
        localStorage.save(KEY, true)
    }

    companion object {
        private const val KEY = "isPremium"
    }
}