package com.arthurnagy.droidcon.architecture.injection.activity

import android.content.Context
import com.arthurnagy.droidcon.feature.MainActivity
import com.arthurnagy.droidcon.feature.session.SessionDetailActivity
import com.arthurnagy.droidcon.architecture.injection.fragment.FragmentBuilderModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, FragmentBuilderModule::class))
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSessionDetailActivity(): SessionDetailActivity

    @Module
    object MainActivityModule {
        @ActivityContext
        @Provides
        @JvmStatic
        fun provideContext(activity: MainActivity): Context = activity
    }

}