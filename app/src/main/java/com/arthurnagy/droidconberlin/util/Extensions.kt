package com.arthurnagy.droidconberlin.util

import android.content.Context
import android.support.annotation.*
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


fun FragmentManager.replace(@IdRes containerId: Int, fragment: Fragment) {
    this.beginTransaction().replace(containerId, fragment).commit()
}

fun Fragment.setupToolbar(toolbar: Toolbar) {
    val activity = (this.activity as AppCompatActivity)
    activity.setSupportActionBar(toolbar)
}

fun Context.dimension(@DimenRes dimension: Int) = resources.getDimensionPixelSize(dimension)

fun Context.drawable(@DrawableRes drawable: Int) = AppCompatResources.getDrawable(this, drawable)

@ColorInt
fun Context.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun View.showSnackbar(@StringRes message: Int, length: Int = Snackbar.LENGTH_LONG, @StringRes action: Int = 0, actionListener: (view: View) -> Unit = {}) {
    val snackbar = Snackbar.make(this, message, length)
    if (action != 0) {
        snackbar.setAction(action, actionListener)
    }
    snackbar.show()
}

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}