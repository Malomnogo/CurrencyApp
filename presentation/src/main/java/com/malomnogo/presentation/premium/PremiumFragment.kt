package com.malomnogo.presentation.premium

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.malomnogo.presentation.core.BaseFragment
import com.malomnogo.presentation.core.UpdateUi
import ru.easycode.presentation.databinding.FragmentPremiumBinding

class PremiumFragment : BaseFragment<FragmentPremiumBinding, PremiumViewModel>() {

    override val viewModelClass = PremiumViewModel::class.java
    private lateinit var updateUi: UpdateUi<PremiumUiState>

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPremiumBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showSuccess = ShowSuccess.Base(requireContext())

        updateUi = object : UpdateUi<PremiumUiState> {
            override fun updateUi(uiState: PremiumUiState) {
                uiState.show(
                    buyButton = binding.buyButton,
                    noButton = binding.noButton,
                    progressBar = binding.progressBar,
                    premiumTextView = binding.premiumTextView,
                    showSuccess = showSuccess
                )
            }
        }

        binding.buyButton.setOnClickListener {
            viewModel.buy()
        }
        binding.noButton.setOnClickListener {
            viewModel.navigateToSettings()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

}

interface ShowSuccess {

    fun showSuccess(message: String)

    class Base(private val context: Context) : ShowSuccess {

        override fun showSuccess(message: String) {
            Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}