package com.arthurnagy.droidconberlin.injection.activity

import android.content.Context
import com.arthurnagy.droidconberlin.feature.MainActivity
import com.arthurnagy.droidconberlin.injection.fragment.FragmentBuilderModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, FragmentBuilderModule::class))
    abstract fun contributeMainActivity(): MainActivity

    @Module
    object MainActivityModule {
        @ActivityContext
        @Provides
        @JvmStatic
        fun provideContext(activity: MainActivity): Context = activity
    }

}