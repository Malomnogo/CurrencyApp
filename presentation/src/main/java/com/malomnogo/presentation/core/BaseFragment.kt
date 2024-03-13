package com.malomnogo.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container)
        return binding.root
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