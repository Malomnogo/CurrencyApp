package com.malomnogo.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.easycode.presentation.databinding.CurrencyPairBinding
import ru.easycode.presentation.databinding.EmptyBinding
import ru.easycode.presentation.databinding.ErrorBinding
import ru.easycode.presentation.databinding.ProgressBinding

interface TypeUi {

    fun viewHolder(
        parent: ViewGroup,
        retry: Retry
    ): DashboardAdapter.DashboardViewHolder

    object Base : TypeUi {

        override fun viewHolder(parent: ViewGroup, retry: Retry) =
            DashboardAdapter.DashboardViewHolder.Base(
                CurrencyPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    object Empty : TypeUi {

        override fun viewHolder(parent: ViewGroup, retry: Retry) =
            DashboardAdapter.DashboardViewHolder.Empty(
                EmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object Progress : TypeUi {

        override fun viewHolder(parent: ViewGroup, retry: Retry) =
            DashboardAdapter.DashboardViewHolder.Progress(
                ProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    object Error : TypeUi {

        override fun viewHolder(parent: ViewGroup, retry: Retry) =
            DashboardAdapter.DashboardViewHolder.Error(
                ErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                retry
            )
    }
}