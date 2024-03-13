package com.malomnogo

import android.content.Context
import com.malomnogo.domain.LocalStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BaseLocalStorage @Inject constructor(
    @ApplicationContext context: Context
) : LocalStorage.Mutable {

    private val sharedPreferences =
        context.getSharedPreferences("premiumStorage", Context.MODE_PRIVATE)

    override fun save(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun read(key: String, default: Boolean) = sharedPreferences.getBoolean(key, default)
}