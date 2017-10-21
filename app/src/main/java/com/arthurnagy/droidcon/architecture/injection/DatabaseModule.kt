package com.arthurnagy.droidcon.architecture.injection

import android.arch.persistence.room.Room
import android.content.Context
import com.arthurnagy.droidcon.architecture.injection.app.AppContext
import com.arthurnagy.droidcon.storage.database.DroidconDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object DatabaseModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideDroidconDatabase(@AppContext context: Context): DroidconDatabase
            = Room.databaseBuilder(context, DroidconDatabase::class.java, "droidcon-db").build()

}