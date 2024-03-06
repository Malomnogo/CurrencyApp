package com.malomnogo.presentation.dashboard

import ru.easycode.presentation.databinding.CurrencyPairBinding
import ru.easycode.presentation.databinding.ErrorBinding

interface DashboardUi {

    fun id(): String
    fun type(): DashboardTypeUi
    fun show(binding: CurrencyPairBinding) = Unit
    fun show(errorBinding: ErrorBinding) = Unit
    fun remove(clickActions: ClickActions) = Unit

    data class Base(
        private val pair: String, private val rate: String
    ) : DashboardUi {

        override fun id() = pair

        override fun type() = DashboardTypeUi.Base

        override fun remove(clickActions: ClickActions) {
            clickActions.remove(pair)
        }

        override fun show(binding: CurrencyPairBinding) = with(binding) {
            pairTextView.text = pair
            currencyTextView.text = rate
        }
    }

    object Empty : DashboardUi {

        override fun id() = "empty"

        override fun type() = DashboardTypeUi.Empty
    }

    object Progress : DashboardUi {

        override fun id() = "progress"

        override fun type() = DashboardTypeUi.Progress
    }

    data class Error(private val message: String) : DashboardUi {

        override fun id() = "error $message"

        override fun type() = DashboardTypeUi.Error

        override fun show(errorBinding: ErrorBinding) = with(errorBinding) {
            errorTextView.text = message
        }
    }
}