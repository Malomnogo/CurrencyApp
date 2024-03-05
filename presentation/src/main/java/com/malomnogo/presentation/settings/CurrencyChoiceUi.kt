package com.malomnogo.presentation.settings

import android.view.View
import ru.easycode.presentation.databinding.CurrencyChoiceBinding

interface CurrencyChoiceUi {

    fun id(): String
    fun type(): SettingsTypeUi
    fun selected(): Boolean = false
    fun show(binding: CurrencyChoiceBinding) = Unit

    data class Base(
        private val currency: String,
        private val isSelected: Boolean = false
    ) : CurrencyChoiceUi {

        override fun id() = currency
        override fun type() = SettingsTypeUi.Base

        override fun selected() = isSelected

        override fun show(binding: CurrencyChoiceBinding) = with(binding) {
            currencyTextView.text = currency
            selectedImageView.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
        }
    }

    object Empty : CurrencyChoiceUi {

        override fun id() = "empty"

        override fun type() = SettingsTypeUi.Empty
    }
}