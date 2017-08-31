package com.arthurnagy.droidconberlin

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.arthurnagy.droidconberlin.feature.shared.ViewModelBoundAdapter


@BindingAdapter("android:textStyle")
fun setTextStyle(textView: TextView, textStyle: Int) {
    textView.setTypeface(textView.typeface, textStyle)
}

@BindingAdapter(value = *arrayOf("adapter", "adapterItemClickListener"), requireAll = false)
fun setupAdapter(recyclerView: RecyclerView, adapter: ViewModelBoundAdapter<*, *>?, adapterItemClickListener: ViewModelBoundAdapter.AdapterItemClickListener) {
    if (adapter != null) {
        recyclerView.adapter = adapter
    }
    if (recyclerView.adapter != null) {
        (recyclerView.adapter as ViewModelBoundAdapter<*, *>).setItemClickListener(adapterItemClickListener)
    }
}