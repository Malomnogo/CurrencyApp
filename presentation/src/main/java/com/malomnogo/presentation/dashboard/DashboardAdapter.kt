package com.malomnogo.presentation.dashboard

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.presentation.databinding.CurrencyPairBinding
import ru.easycode.presentation.databinding.EmptyBinding
import ru.easycode.presentation.databinding.ErrorBinding
import ru.easycode.presentation.databinding.ProgressBinding

class DashboardAdapter(
    private val clickActions: ClickActions,
    private val types: List<DashboardTypeUi> = listOf(
        DashboardTypeUi.Base,
        DashboardTypeUi.Progress,
        DashboardTypeUi.Empty,
        DashboardTypeUi.Error
    )
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>(), ShowList {

    private val currencies = mutableListOf<DashboardUi>()

    override fun getItemViewType(position: Int): Int {
        val type = currencies[position].type()
        val index = types.indexOf(type)
        if (index == -1)
            throw IllegalStateException("Type $type is not included in the typeList $types")
        return index
    }

    override fun show(list: List<DashboardUi>) {
        val diffResult =
            DiffUtil.calculateDiff(DashboardDiffUtilCallback(oldList = currencies, newList = list))
        currencies.clear()
        currencies.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        types[viewType].viewHolder(parent, clickActions)

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    abstract class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        open fun bind(item: DashboardUi) = Unit

        class Progress(binding: ProgressBinding) : DashboardViewHolder(binding.root)

        class Empty(binding: EmptyBinding) : DashboardViewHolder(binding.root)

        class Error(private val binding: ErrorBinding, private val clickListener: ClickActions) :
            DashboardViewHolder(binding.root) {

            override fun bind(item: DashboardUi) {
                item.show(binding)
                binding.retryButton.setOnClickListener { clickListener.retry() }
            }
        }

        class Base(
            private val binding: CurrencyPairBinding,
            private val clickListener: ClickActions
        ) : DashboardViewHolder(binding.root) {

            override fun bind(item: DashboardUi) {
                item.show(binding)
                binding.deleteImageButton.setOnClickListener {
                    item.remove(clickListener)
                }
            }
        }
    }
}