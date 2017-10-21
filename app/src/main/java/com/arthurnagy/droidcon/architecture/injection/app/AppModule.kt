package com.arthurnagy.droidcon.architecture.injection.app

import android.content.Context
import com.arthurnagy.droidcon.DroidconApplication
import com.arthurnagy.droidcon.architecture.injection.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


/**
 * Dagger module providing the application context.
 */
@Module(includes = arrayOf(ViewModelModule::class))
abstract class AppModule {

    @Singleton
    @AppContext
    @Binds
    abstract fun bindsContext(app: DroidconApplication): Context

}