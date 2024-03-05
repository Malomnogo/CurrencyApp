package com.malomnogo.presentation.settings

import androidx.recyclerview.widget.DiffUtil

class CurrencyDiffUtilCallback(
    private val oldList: List<CurrencyChoiceUi>,
    private val newList: List<CurrencyChoiceUi>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}