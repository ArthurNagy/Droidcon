package com.arthurnagy.droidcon.feature.schedule.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.ScheduleSessionItemBinding
import com.arthurnagy.droidcon.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidcon.model.Session

class ScheduleAdapter : ViewModelBoundAdapter<ScheduleSessionItemBinding, ScheduleItemViewModel>() {

    private var items: MutableList<Session> = mutableListOf()
    private var itemSavedClickListener: (position: Int) -> Unit = { _ -> }

    fun replace(newItems: List<Session>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, session: Session) {
        items[position] = session
        notifyItemChanged(position, SCHEDULE_PAYLOAD)
    }

    fun getItem(position: Int) = items[position]

    fun setItemSavedClickListener(itemSavedClickListener: (position: Int) -> Unit) {
        this.itemSavedClickListener = itemSavedClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ScheduleSessionItemBinding, ScheduleItemViewModel> {
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        viewHolder.binding.sessionSaved.setOnClickListener { itemSavedClickListener.invoke(recyclerView?.getChildAdapterPosition(viewHolder.itemView) ?: RecyclerView.NO_POSITION) }
        return viewHolder
    }

    override fun getItemLayoutId(position: Int) = R.layout.item_schedule_session

    override fun createViewModel(viewType: Int) = ScheduleItemViewModel()

    override fun getItemCount() = items.size

    override fun bindItem(holder: BindingViewHolder<ScheduleSessionItemBinding, ScheduleItemViewModel>, position: Int, payloads: List<Any>) {
        holder.viewModel.scheduleSession.set(items[position])
    }

    companion object {
        val SCHEDULE_PAYLOAD = Any()
    }

}