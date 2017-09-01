package com.arthurnagy.droidconberlin.feature.schedule.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.ScheduleSessionItemBinding
import com.arthurnagy.droidconberlin.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidconberlin.model.Session

class ScheduleAdapter : ViewModelBoundAdapter<ScheduleSessionItemBinding, ScheduleItemViewModel>() {

    private var items: MutableList<Session> = mutableListOf()
    private var itemSavedClickListener: (position: Int) -> Unit = { _ -> }

    fun replace(newItems: List<Session>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
//        if (items.isEmpty()) {
//            items.addAll(newItems)
//            notifyDataSetChanged()
//        } else {
//            val oldItems = items.toList()
//            items.clear()
//            items.addAll(newItems)
//            val result =
//                    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
//
//                        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
//                                = oldItems[oldItemPosition].id == newItems[newItemPosition].id
//
//                        override fun getOldListSize() = oldItems.size
//
//                        override fun getNewListSize() = newItems.size
//
//                        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
//                                = oldItems[oldItemPosition].id == newItems[newItemPosition].id
//                                && oldItems[oldItemPosition].title == newItems[newItemPosition].title
//                                && oldItems[oldItemPosition].description == newItems[newItemPosition].description
//                                && oldItems[oldItemPosition].startDate == newItems[newItemPosition].startDate
//                                && oldItems[oldItemPosition].endDate == newItems[newItemPosition].endDate
//                                && oldItems[oldItemPosition].isSaved == newItems[newItemPosition].isSaved
//                    })
//            result.dispatchUpdatesTo(this)
//        }
    }

    fun updateItem(position: Int, session: Session) {
        items[position] = session
        notifyItemChanged(position)
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
        holder.viewModel.scheduleSession = items[position]
    }
}