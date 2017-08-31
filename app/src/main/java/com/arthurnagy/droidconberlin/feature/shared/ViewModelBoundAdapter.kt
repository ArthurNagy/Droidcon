package com.arthurnagy.droidconberlin.feature.shared

import android.databinding.DataBindingUtil
import android.databinding.OnRebindCallback
import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel

abstract class ViewModelBoundAdapter<VB : ViewDataBinding, VM : DroidconViewModel> : RecyclerView.Adapter<ViewModelBoundAdapter.BindingViewHolder<VB, VM>>() {

    private var recyclerView: RecyclerView? = null
    private var itemClickListener: (position: Int) -> Unit = { _ -> }
    private val rebindCallback: OnRebindCallback<VB>

    init {
        rebindCallback = object : OnRebindCallback<VB>() {
            override fun onPreBind(binding: VB) = recyclerView?.let { recyclerView ->
                val childAdapterPosition = recyclerView.getChildAdapterPosition(binding.root)
                when {
                    recyclerView.isComputingLayout -> true
                    childAdapterPosition == RecyclerView.NO_POSITION -> true
                    else -> {
                        notifyItemChanged(childAdapterPosition, DB_PAYLOAD)
                        false
                    }
                }
            } ?: true

        }
    }

    @LayoutRes
    protected abstract fun getItemLayoutId(position: Int): Int

    protected abstract fun bindItem(holder: BindingViewHolder<VB, VM>, position: Int, payloads: List<Any>)

    protected abstract fun createViewModel(@LayoutRes viewType: Int): VM

    fun setItemClickListener(itemClickListener: AdapterItemClickListener) {
        this.itemClickListener = {
            itemClickListener.onItemClicked(it)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = null
    }

    override fun onBindViewHolder(holder: BindingViewHolder<VB, VM>, position: Int, payloads: MutableList<Any>) {
        // when a VH is rebound to the same item, we don't have to call the setters
        if (payloads.isEmpty() || payloads.any { it !== DB_PAYLOAD }) {
            bindItem(holder, position, payloads)
        }
        holder.binding.executePendingBindings()
    }

    override final fun onBindViewHolder(holder: BindingViewHolder<VB, VM>, position: Int) {
        throw IllegalArgumentException("Just overridden to make final.")
    }

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): BindingViewHolder<VB, VM> {
        val viewHolder = BindingViewHolder.create<VB, VM>(parent, viewType, createViewModel(viewType))
        viewHolder.binding.addOnRebindCallback(rebindCallback)
        viewHolder.setItemClickListener(itemClickListener)
        return viewHolder
    }

    override fun getItemViewType(position: Int) = getItemLayoutId(position)

    companion object {
        private val DB_PAYLOAD = Any()
    }

    interface AdapterItemClickListener {

        fun onItemClicked(position: Int)
    }

    class BindingViewHolder<out VB : ViewDataBinding, out VM : Any>
    private constructor(val binding: VB, val viewModel: VM) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            @JvmStatic fun <VB : ViewDataBinding, VM : Any> create(parent: ViewGroup, @LayoutRes layoutId: Int, viewModel: VM): BindingViewHolder<VB, VM> {
                val binding: VB = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
                return BindingViewHolder(binding, viewModel)
            }
        }

        init {
            binding.setVariable(BR.viewModel, viewModel)
        }

        fun setItemClickListener(itemClickListener: ((position: Int) -> Unit)?) {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(adapterPosition)
                }
            }
        }
    }
}