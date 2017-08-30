package com.arthurnagy.droidconberlin.feature.agenda

import android.support.annotation.LayoutRes
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.SessionItemBinding
import com.arthurnagy.droidconberlin.feature.shared.ViewModelBoundAdapter

class MyAgendaAdapter : ViewModelBoundAdapter<SessionItemBinding, SessionItemViewModel>() {
    private val items = mutableListOf<String>()

    override fun getItemLayoutId(position: Int) = R.layout.item_session

    override fun bindItem(holder: BindingViewHolder<SessionItemBinding, SessionItemViewModel>, position: Int, payloads: List<Any>) = holder.viewModel.title.set(items[position])

    override fun createViewModel(@LayoutRes viewType: Int) = SessionItemViewModel()

    override fun getItemCount() = items.size

    fun setItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}