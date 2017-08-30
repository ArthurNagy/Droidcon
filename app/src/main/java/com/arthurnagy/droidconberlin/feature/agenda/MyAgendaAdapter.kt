package com.arthurnagy.droidconberlin.feature.agenda

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.feature.ViewModelBoundAdapter

class MyAgendaAdapter : ViewModelBoundAdapter<ViewDataBinding, DroidconViewModel>() {
    private val items = mutableListOf<Any>()

    override fun getItemLayoutId(position: Int) = when (items[position]) {
        is String -> R.layout.item_session
        else -> R.layout.item_header
    }

    override fun bindItem(holder: BindingViewHolder<ViewDataBinding, DroidconViewModel>, position: Int, payloads: List<Any>) = when (holder.itemViewType) {
        R.layout.item_session -> (holder.viewModel as SessionItemViewModel).title.set(items[position] as String)
        else -> (holder.viewModel as HeaderItemViewModel).title.set((items[position] as Int).toString())
    }

    override fun createViewModel(@LayoutRes viewType: Int) = when (viewType) {
        R.layout.item_session -> SessionItemViewModel()
        else -> HeaderItemViewModel()
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<Any>) {
        this.items.clear()
        this.items.addAll(items.filter { it is String || it is Int })
        notifyDataSetChanged()
    }
}