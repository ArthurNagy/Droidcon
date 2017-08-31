package com.arthurnagy.droidconberlin.feature.agenda.list

import android.support.v7.util.DiffUtil
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.MyAgendaSessionItemBinding
import com.arthurnagy.droidconberlin.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidconberlin.model.Session

class MyAgendaAdapter : ViewModelBoundAdapter<MyAgendaSessionItemBinding, MyAgendaItemViewModel>() {

    private var items: MutableList<Session> = mutableListOf()

    fun replace(newItems: List<Session>) {
        if (items.isEmpty()) {
            items.addAll(newItems)
            notifyDataSetChanged()
        } else {
            val oldItems = items.toList()
            items.clear()
            items.addAll(newItems)
            val result =
                    DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
                                = oldItems[oldItemPosition].id == newItems[newItemPosition].id

                        override fun getOldListSize() = oldItems.size

                        override fun getNewListSize() = newItems.size

                        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
                                = oldItems[oldItemPosition].id == newItems[newItemPosition].id
                                && oldItems[oldItemPosition].title == newItems[newItemPosition].title
                                && oldItems[oldItemPosition].description == newItems[newItemPosition].description
                                && oldItems[oldItemPosition].startDate == newItems[newItemPosition].startDate
                                && oldItems[oldItemPosition].endDate == newItems[newItemPosition].endDate
                    })
            result.dispatchUpdatesTo(this)
        }
    }

    fun getItem(position: Int) = items[position]

    override fun getItemLayoutId(position: Int) = R.layout.item_my_agenda_session

    override fun createViewModel(viewType: Int) = MyAgendaItemViewModel()

    override fun getItemCount() = items.size

    override fun bindItem(holder: BindingViewHolder<MyAgendaSessionItemBinding, MyAgendaItemViewModel>, position: Int, payloads: List<Any>) {
    }
}