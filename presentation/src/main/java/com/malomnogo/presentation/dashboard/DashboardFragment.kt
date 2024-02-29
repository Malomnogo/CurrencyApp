package com.malomnogo.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.malomnogo.presentation.core.BaseFragment
import com.malomnogo.presentation.core.UpdateUi
import ru.easycode.presentation.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {

    private lateinit var updateUi: UpdateUi<DashboardUiState>

    override val viewModelClass = DashboardViewModel::class.java

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DashboardAdapter(viewModel)

        binding.recyclerView.adapter = adapter

        binding.settingsButton.setOnClickListener {
            viewModel.goToSettings()
        }

        updateUi = object : UpdateUi<DashboardUiState> {
            override fun updateUi(uiState: DashboardUiState) {
                uiState.update(adapter)
            }
        }
        viewModel.load()
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