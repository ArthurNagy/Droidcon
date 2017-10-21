package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.arthurnagy.droidcon.model.Session

@Database(entities = arrayOf(Session::class), version = 1)
@TypeConverters(value = *arrayOf(Converters::class))
abstract class DroidconDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao

}