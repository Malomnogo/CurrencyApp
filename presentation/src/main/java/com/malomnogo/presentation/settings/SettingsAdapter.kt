package com.malomnogo.presentation.settings

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.presentation.databinding.CurrencyChoiceBinding
import ru.easycode.presentation.databinding.HintBinding

class SettingsAdapter(
    private val choose: (String) -> Unit,
    private val types: List<SettingsTypeUi> = listOf(SettingsTypeUi.Base, SettingsTypeUi.Empty)
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>(), ShowCurrencies {

    private val currencies = mutableListOf<CurrencyChoiceUi>()

    override fun getItemViewType(position: Int): Int {
        val type = currencies[position].type()
        val index = types.indexOf(type)
        if (index == -1)
            throw IllegalStateException("Type $type is not included in the typeList $types")
        return index
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        types[viewType].viewHolder(parent, choose)

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun show(list: List<CurrencyChoiceUi>) {
        val diffResult = DiffUtil.calculateDiff(CurrencyDiffUtilCallback(currencies, list))
        currencies.clear()
        currencies.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun selected() = currencies.find { it.selected() }?.id() ?: ""

    abstract class SettingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        open fun bind(currency: CurrencyChoiceUi) = Unit

        class Base(
            private val binding: CurrencyChoiceBinding,
            private val choose: (String) -> Unit
        ) : SettingsViewHolder(binding.root) {

            override fun bind(currency: CurrencyChoiceUi) {
                currency.show(binding)
                binding.choiceLayout.setOnClickListener {
                    choose.invoke(binding.currencyTextView.text.toString())
                }
            }
        }

        class Hint(binding: HintBinding) : SettingsViewHolder(binding.root)
    }
}