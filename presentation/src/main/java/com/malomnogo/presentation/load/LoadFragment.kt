package com.malomnogo.presentation.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.malomnogo.presentation.core.BaseFragment
import com.malomnogo.presentation.core.UpdateUi
import dagger.hilt.android.AndroidEntryPoint
import ru.easycode.presentation.databinding.FragmentLoadBinding

@AndroidEntryPoint
class LoadFragment : BaseFragment<FragmentLoadBinding, LoadViewModel>() {

    private lateinit var updateUi: UpdateUi<LoadUiState>

    override val viewModel: LoadViewModel by viewModels()
    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoadBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) {
                uiState.update(
                    progressBar = binding.progress.progressBar,
                    customTextActionsView = binding.error.errorTextView,
                    retryButton = binding.error.retryButton
                )
            }
        }

        binding.error.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.init(isFirstRun = savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer = updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}