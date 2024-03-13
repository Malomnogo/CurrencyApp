package com.malomnogo.presentation.premium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.malomnogo.presentation.core.BaseFragment
import com.malomnogo.presentation.core.UpdateUi
import dagger.hilt.android.AndroidEntryPoint
import ru.easycode.presentation.databinding.FragmentPremiumBinding

@AndroidEntryPoint
class PremiumFragment : BaseFragment<FragmentPremiumBinding, PremiumViewModel>() {

    private lateinit var updateUi: UpdateUi<PremiumUiState>
    override val viewModel: PremiumViewModel by viewModels()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPremiumBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi = object : UpdateUi<PremiumUiState> {
            override fun updateUi(uiState: PremiumUiState) {
                uiState.show(
                    buyButton = binding.buyButton,
                    noButton = binding.noButton,
                    progressBar = binding.progressBar,
                    premiumTextView = binding.premiumTextView
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