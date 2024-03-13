package com.malomnogo.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.malomnogo.presentation.core.BaseFragment
import com.malomnogo.presentation.core.UpdateUi
import dagger.hilt.android.AndroidEntryPoint
import ru.easycode.presentation.databinding.FragmentSettingsBinding

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels()
    private lateinit var updateUi: UpdateUi<SettingsUiState>
    private lateinit var fromAdapter: SettingsAdapter
    private lateinit var toAdapter: SettingsAdapter

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fromAdapter = SettingsAdapter(choose = viewModel::choose)
        toAdapter = SettingsAdapter(choose = { to ->
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

        viewModel.init(BundleWrapper.Base(savedInstanceState))

        updateUi = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) {
                uiState.show(fromAdapter, toAdapter, binding.saveButton)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.navigateToDashboard()
                }
            })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        BundleWrapper.Base(outState)
            .save(fromAdapter.selected(), toAdapter.selected())
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