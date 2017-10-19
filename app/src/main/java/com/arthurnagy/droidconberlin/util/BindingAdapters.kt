package com.arthurnagy.droidconberlin.util

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.widget.ImageView
import android.widget.TextView


@BindingAdapter("android:textStyle")
fun setTextStyle(textView: TextView, textStyle: Int) {
    textView.setTypeface(textView.typeface, textStyle)
}

@BindingAdapter("android:textAppearance")
fun setTextAppearance(textView: TextView, @StyleRes textAppearance: Int) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        textView.setTextAppearance(textAppearance)
    } else {
        @Suppress("DEPRECATION")
        textView.setTextAppearance(textView.context, textAppearance)
    }
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, @DrawableRes drawableResource: Int) {
    imageView.setImageResource(drawableResource)
}