package com.malomnogo.data.core

import android.content.Context
import com.malomnogo.domain.LocalStorage

class BaseLocalStorage(context: Context) : LocalStorage.Mutable {

    private val sharedPreferences =
        context.getSharedPreferences("premiumStorage", Context.MODE_PRIVATE)

    override fun save(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun read(key: String, default: Boolean) = sharedPreferences.getBoolean(key, default)
}