package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.model.Speaker
import com.arthurnagy.droidcon.model.Term

@Database(entities = arrayOf(Session::class, Speaker::class, Term::class), version = 2)
@TypeConverters(value = *arrayOf(Converters::class))
abstract class DroidconDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao

}