package com.arthurnagy.droidconberlin.architecture

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class DroidconFragment : DaggerFragment() {

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    protected fun <VM : DroidconViewModel> getViewModel(viewModelType: Class<VM>): VM {
        return ViewModelProviders.of(this, viewModelProviderFactory).get(viewModelType)
    }

}