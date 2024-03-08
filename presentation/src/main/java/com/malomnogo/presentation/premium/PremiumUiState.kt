package com.malomnogo.presentation.premium

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.core.views.CustomButtonActions
import com.malomnogo.presentation.core.views.CustomTextActions

interface PremiumUiState {

    fun show(
        buyButton: CustomButtonActions,
        noButton: ChangeVisibility,
        progressBar: ChangeVisibility,
        premiumTextView: CustomTextActions,
    )

    data class Initial(private val description: String) : PremiumUiState {

        override fun show(
            buyButton: CustomButtonActions,
            noButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            premiumTextView: CustomTextActions
        ) {
            buyButton.show()
            noButton.show()
            progressBar.hide()
            premiumTextView.changeText(description)
            premiumTextView.changeTextColor("#000000")
        }
    }

    object Progress : PremiumUiState {

        override fun show(
            buyButton: CustomButtonActions,
            noButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            premiumTextView: CustomTextActions
        ) {
            buyButton.hide()
            noButton.hide()
            premiumTextView.hide()
            progressBar.show()
        }
    }

    data class Error(private val message: String) : PremiumUiState {

        override fun show(
            buyButton: CustomButtonActions,
            noButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            premiumTextView: CustomTextActions
        ) {
            premiumTextView.show()
            premiumTextView.changeTextColor("#FF0000")
            premiumTextView.changeText(message)
            buyButton.show()
            noButton.show()
            progressBar.hide()
        }
    }

    object Empty : PremiumUiState {

        override fun show(
            buyButton: CustomButtonActions,
            noButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            premiumTextView: CustomTextActions
        ) = Unit
    }
}