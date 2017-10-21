package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.arthurnagy.droidcon.model.Session
import io.reactivex.Flowable

@Dao
interface SessionDao {

    @Query("SELECT * FROM session")
    fun getAll(): Flowable<List<Session>>

    @Insert
    fun insertAll(vararg sessions: Session)

    @Delete
    fun delete(session: Session)

}