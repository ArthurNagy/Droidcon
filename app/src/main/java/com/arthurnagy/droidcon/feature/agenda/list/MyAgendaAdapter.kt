package com.arthurnagy.droidcon.feature.agenda.list

import com.arthurnagy.droidcon.MyAgendaSessionItemBinding
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidcon.model.Session

class MyAgendaAdapter : ViewModelBoundAdapter<MyAgendaSessionItemBinding, MyAgendaItemViewModel>() {

    private var items: MutableList<Session> = mutableListOf()
    private var itemToBeRemoved: Session? = null
    var positionOfItemToBeRemoved = 0

    fun replace(newItems: List<Session>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = if (position < 0) itemToBeRemoved!! else items[position]

    fun canRemoveItem(position: Int) = itemToBeRemoved == null && !Session.isIntermission(items[position])

    fun removeItem(position: Int) {
        positionOfItemToBeRemoved = position
        itemToBeRemoved = items[position]
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeItemToBeRemoved(doOnRemove: (item: Session) -> Unit) {
        itemToBeRemoved?.let {
            doOnRemove.invoke(it)
            itemToBeRemoved = null
        }
    }

    fun undoItemRemoval() {
        itemToBeRemoved?.let {
            items.add(positionOfItemToBeRemoved, it)
            notifyItemInserted(positionOfItemToBeRemoved)
            itemToBeRemoved = null
        }
    }

    override fun getItemLayoutId(position: Int) = R.layout.item_my_agenda_session

    override fun createViewModel(viewType: Int) = MyAgendaItemViewModel()

    override fun getItemCount() = items.size

    override fun bindItem(holder: BindingViewHolder<MyAgendaSessionItemBinding, MyAgendaItemViewModel>, position: Int, payloads: List<Any>) {
        holder.viewModel.scheduleSession.set(items[position])
    }
}