package com.malomnogo.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.easycode.presentation.databinding.CurrencyChoiceBinding
import ru.easycode.presentation.databinding.HintBinding

interface SettingsTypeUi {

    fun viewHolder(parent: ViewGroup, choose: (String) -> Unit): SettingsAdapter.SettingsViewHolder

    object Base : SettingsTypeUi {

        override fun viewHolder(parent: ViewGroup, choose: (String) -> Unit) =
            SettingsAdapter.SettingsViewHolder.Base(
                CurrencyChoiceBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), choose
            )
    }

    object Empty : SettingsTypeUi {

        override fun viewHolder(parent: ViewGroup, choose: (String) -> Unit) =
            SettingsAdapter.SettingsViewHolder.Hint(
                HintBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}