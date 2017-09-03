package com.arthurnagy.droidconberlin.injection

import com.arthurnagy.droidconberlin.SessionJobService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSessionJobService(): SessionJobService

}