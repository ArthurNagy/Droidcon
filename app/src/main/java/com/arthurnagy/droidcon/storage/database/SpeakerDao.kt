package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.arthurnagy.droidcon.model.Speaker

@Dao
interface SpeakerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg speakers: Speaker)

    @Delete
    fun delete(speaker: Speaker)

}