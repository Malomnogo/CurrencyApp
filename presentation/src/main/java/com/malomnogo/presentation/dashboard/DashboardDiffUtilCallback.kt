package com.malomnogo.presentation.dashboard

import androidx.recyclerview.widget.DiffUtil

class DashboardDiffUtilCallback(
    private val oldList: List<DashboardUi>,
    private val newList: List<DashboardUi>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}