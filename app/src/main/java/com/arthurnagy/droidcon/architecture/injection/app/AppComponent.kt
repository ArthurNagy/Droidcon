package com.arthurnagy.droidcon.architecture.injection.app

import com.arthurnagy.droidcon.DroidconApplication
import com.arthurnagy.droidcon.architecture.injection.DroidconApiModule
import com.arthurnagy.droidcon.architecture.injection.activity.ActivityBuilderModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AppModule::class, DroidconApiModule::class, ActivityBuilderModule::class))
interface AppComponent : AndroidInjector<DroidconApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DroidconApplication>()

}