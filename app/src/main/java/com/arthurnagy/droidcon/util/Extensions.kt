package com.arthurnagy.droidcon.util

import android.content.Context
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.net.Uri
import android.support.annotation.*
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.view.View
import com.arthurnagy.droidcon.R
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
fun Context?.color(@ColorRes color: Int) = if (this == null) 0 else ContextCompat.getColor(this, color)

fun Context.openUrl(url: String) = CustomTabsIntent.Builder()
        .setToolbarColor(this.color(R.color.primary))
        .setShowTitle(true)
        .build().launchUrl(this, Uri.parse(url))

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun View.showSnackbar(@StringRes message: Int, length: Int = Snackbar.LENGTH_LONG, @StringRes action: Int = 0, actionListener: (view: View) -> Unit = {}) {
    val snackbar = Snackbar.make(this, message, length)
    if (action != 0) {
        snackbar.setAction(action, actionListener)
    }
    snackbar.show()
}

inline fun <T, R> ObservableField<R>.dependsOn(dependableField: ObservableField<T>, crossinline mapper: (T) -> R): ObservableField<R> {
    dependableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            set(mapper(dependableField.get()))
        }
    })
    return this
}

inline fun <R> ObservableField<R>.dependsOn(vararg dependableFields: ObservableField<out Any>, crossinline mapper: () -> R): ObservableField<R> {
    dependableFields.forEach { dependableField ->
        dependableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                set(mapper())
            }
        })
    }
    return this
}

inline fun <T> ObservableBoolean.dependsOn(dependableField: ObservableField<T>, crossinline mapper: (T) -> Boolean): ObservableBoolean {
    dependableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            set(mapper(dependableField.get()))
        }
    })
    return this
}

inline fun <T> ObservableInt.dependsOn(observableField: ObservableField<T>, crossinline mapper: (T) -> Int): ObservableInt {
    observableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            set(mapper(observableField.get()))
        }
    })
    return this
}

inline fun <R> ObservableField<R>.observe(crossinline observer: (R) -> Unit) {
    this.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            observer(get())
        }
    })
}