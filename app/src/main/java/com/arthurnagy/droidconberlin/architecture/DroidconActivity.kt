package com.arthurnagy.droidconberlin.architecture

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@SuppressLint("Registered")
open class DroidconActivity : DaggerAppCompatActivity(), LifecycleRegistryOwner {

    @Inject lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val lifecycleRegistry: LifecycleRegistry
        get() = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    protected fun <VM : DroidconViewModel> getViewModel(viewModelType: Class<VM>): VM {
        return ViewModelProviders.of(this, viewModelProviderFactory).get(viewModelType)
    }

}