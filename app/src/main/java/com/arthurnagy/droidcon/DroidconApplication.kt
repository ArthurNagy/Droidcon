package com.arthurnagy.droidcon

import com.arthurnagy.droidcon.injection.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class DroidconApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}