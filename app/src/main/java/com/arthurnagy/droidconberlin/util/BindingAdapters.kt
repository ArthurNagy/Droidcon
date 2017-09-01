package com.arthurnagy.droidconberlin.util

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.widget.ImageView
import android.widget.TextView


@BindingAdapter("android:textStyle")
fun setTextStyle(textView: TextView, textStyle: Int) {
    textView.setTypeface(textView.typeface, textStyle)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, @DrawableRes drawableResource: Int) {
    imageView.setImageResource(drawableResource)
}

//@BindingAdapter(value = *arrayOf("adapter", "adapterItemClickListener", "adapterItemSavedClickListener"), requireAll = false)
//fun setupAdapter(recyclerView: RecyclerView, adapter: ViewModelBoundAdapter<*, *>?,
//                 adapterItemClickListener: ViewModelBoundAdapter.AdapterItemClickListener?,
//                 adapterItemSavedClickListener: ViewModelBoundAdapter.AdapterItemClickListener?) {
//    if (adapter != null) {
//        recyclerView.adapter = adapter
//    }
//    if (recyclerView.adapter != null) {
//        adapterItemClickListener?.let {
//            (recyclerView.adapter as ViewModelBoundAdapter<*, *>).setItemClickListener(it)
//        }
//        adapterItemSavedClickListener?.let {
//            (recyclerView.adapter as ScheduleAdapter).setItemSavedClickListener(it)
//        }
//    }
//}