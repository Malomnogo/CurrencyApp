package com.malomnogo.presentation.load

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.core.views.CustomTextActions

interface LoadUiState {

    fun update(
        progressBar: ChangeVisibility,
        customTextActionsView: CustomTextActions,
        retryButton: ChangeVisibility
    )

    data class Error(private val message: String) : LoadUiState {

        override fun update(
            progressBar: ChangeVisibility,
            customTextActionsView: CustomTextActions,
            retryButton: ChangeVisibility
        ) {
            customTextActionsView.show()
            customTextActionsView.changeText(message)
            progressBar.hide()
            retryButton.show()
        }
    }

    object Progress : LoadUiState {

        override fun update(
            progressBar: ChangeVisibility,
            customTextActionsView: CustomTextActions,
            retryButton: ChangeVisibility
        ) {
            customTextActionsView.hide()
            progressBar.show()
            retryButton.hide()
        }
    }

    object Empty : LoadUiState {

        override fun update(
            progressBar: ChangeVisibility,
            customTextActionsView: CustomTextActions,
            retryButton: ChangeVisibility
        ) = Unit
    }
}