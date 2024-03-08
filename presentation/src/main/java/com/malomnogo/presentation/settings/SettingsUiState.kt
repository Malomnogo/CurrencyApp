package com.malomnogo.presentation.settings

import com.malomnogo.presentation.core.views.ChangeVisibility

interface SettingsUiState {

    fun show(
        showFrom: ShowCurrencies,
        showTo: ShowCurrencies,
        saveButton: ChangeVisibility
    )

    data class Initial(private val fromList: List<CurrencyChoiceUi>) : SettingsUiState {

        override fun show(
            showFrom: ShowCurrencies,
            showTo: ShowCurrencies, saveButton: ChangeVisibility
        ) {
            showFrom.show(fromList)
            saveButton.hide()
        }
    }

    data class Destinations(
        private val fromList: List<CurrencyChoiceUi>,
        private val toList: List<CurrencyChoiceUi>
    ) : SettingsUiState {

        override fun show(
            showFrom: ShowCurrencies,
            showTo: ShowCurrencies,
            saveButton: ChangeVisibility
        ) {
            showFrom.show(fromList)
            showTo.show(toList)
            saveButton.hide()
        }
    }

    data class ReadyToSave(private val toList: List<CurrencyChoiceUi>) : SettingsUiState {

        override fun show(
            showFrom: ShowCurrencies,
            showTo: ShowCurrencies,
            saveButton: ChangeVisibility
        ) {
            showTo.show(toList)
            saveButton.show()
        }
    }

    data class Hint(private val fromList: List<CurrencyChoiceUi>) : SettingsUiState {

        override fun show(
            showFrom: ShowCurrencies,
            showTo: ShowCurrencies,
            saveButton: ChangeVisibility
        ) {
            showFrom.show(fromList)
            showTo.show(listOf(CurrencyChoiceUi.Empty))
            saveButton.hide()
        }
    }

    object Empty : SettingsUiState {

        override fun show(
            showFrom: ShowCurrencies,
            showTo: ShowCurrencies, saveButton: ChangeVisibility
        ) = Unit
    }
}