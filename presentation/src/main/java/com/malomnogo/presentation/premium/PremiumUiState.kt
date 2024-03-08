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
        showSuccess: ShowSuccess
    )

    data class Initial(private val description: String) : PremiumUiState {

        override fun show(
            buyButton: CustomButtonActions,
            noButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            premiumTextView: CustomTextActions,
            showSuccess: ShowSuccess
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
            premiumTextView: CustomTextActions,
            showSuccess: ShowSuccess
        ) {
            buyButton.hide()
            noButton.hide()
            premiumTextView.hide()
            progressBar.show()
        }
    }

    data class Success(private val successDescription: String) : PremiumUiState {

        override fun show(
            buyButton: CustomButtonActions,
            noButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            premiumTextView: CustomTextActions,
            showSuccess: ShowSuccess
        ) {
            buyButton.hide()
            noButton.hide()
            premiumTextView.hide()
            progressBar.hide()
            showSuccess.showSuccess(successDescription)
        }
    }

    data class Error(private val message: String) : PremiumUiState {

        override fun show(
            buyButton: CustomButtonActions,
            noButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            premiumTextView: CustomTextActions,
            showSuccess: ShowSuccess
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
            premiumTextView: CustomTextActions,
            showSuccess: ShowSuccess
        ) = Unit
    }
}