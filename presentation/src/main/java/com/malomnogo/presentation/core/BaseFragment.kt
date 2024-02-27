package com.malomnogo.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, VM : CustomViewModel> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!
    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(viewModelClass)
    }

    protected abstract fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}