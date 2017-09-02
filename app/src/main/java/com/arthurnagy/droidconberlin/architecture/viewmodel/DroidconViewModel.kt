package com.arthurnagy.droidconberlin.architecture.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

open class DroidconViewModel : ViewModel(), Observable {

    protected val disposables = CompositeDisposable()

    private val propertyCallbacks by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        propertyCallbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        propertyCallbacks.add(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() = propertyCallbacks.notifyChange(this, 0)

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [android.databinding.Bindable] to generate a field in
     * [com.arthurnagy.androidarchitecturesample.BR] to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) = propertyCallbacks.notifyChange(this, fieldId)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}