package com.arthurnagy.droidconberlin.injection.app

import com.arthurnagy.droidconberlin.DroidconApplication
import com.arthurnagy.droidconberlin.injection.DroidconApiModule
import com.arthurnagy.droidconberlin.injection.ServiceBuilderModule
import com.arthurnagy.droidconberlin.injection.activity.ActivityBuilderModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AppModule::class, DroidconApiModule::class, ActivityBuilderModule::class, ServiceBuilderModule::class))
interface AppComponent : AndroidInjector<DroidconApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DroidconApplication>()

}