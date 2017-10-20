package com.arthurnagy.droidcon.architecture.viewmodel

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

open class DroidconViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}