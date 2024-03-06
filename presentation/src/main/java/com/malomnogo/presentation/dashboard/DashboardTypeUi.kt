package com.malomnogo.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.easycode.presentation.databinding.CurrencyPairBinding
import ru.easycode.presentation.databinding.EmptyBinding
import ru.easycode.presentation.databinding.ErrorBinding
import ru.easycode.presentation.databinding.ProgressBinding

interface DashboardTypeUi {

    fun viewHolder(
        parent: ViewGroup,
        clickActions: ClickActions
    ): DashboardAdapter.DashboardViewHolder

    object Base : DashboardTypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Base(
                CurrencyPairBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                clickActions
            )
    }

    object Empty : DashboardTypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Empty(
                EmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    object Progress : DashboardTypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Progress(
                ProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    object Error : DashboardTypeUi {

        override fun viewHolder(parent: ViewGroup, clickActions: ClickActions) =
            DashboardAdapter.DashboardViewHolder.Error(
                ErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                clickActions
            )
    }
}