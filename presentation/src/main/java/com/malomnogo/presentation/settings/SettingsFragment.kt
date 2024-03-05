package com.malomnogo.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.malomnogo.presentation.core.BaseFragment
import com.malomnogo.presentation.core.UpdateUi
import ru.easycode.presentation.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    private lateinit var updateUi: UpdateUi<SettingsUiState>
    override val viewModelClass = SettingsViewModel::class.java

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromAdapter = SettingsAdapter(choose = viewModel::choose)
        val toAdapter = SettingsAdapter(choose = { to ->
            viewModel.chooseDestination(from = fromAdapter.selected(), to = to)
        })

        binding.fromRecyclerView.adapter = fromAdapter
        binding.toRecyclerView.adapter = toAdapter

        binding.saveButton.setOnClickListener {
            viewModel.save(from = fromAdapter.selected(), to = toAdapter.selected())
        }

        binding.backButton.setOnClickListener {
            viewModel.navigateToDashboard()
        }

        viewModel.init()

        updateUi = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) {
                uiState.show(fromAdapter, toAdapter, binding.saveButton)
            }
        }
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