package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.*
import com.arthurnagy.droidcon.model.Session
import io.reactivex.Flowable

@Dao
interface SessionDao {

    @Query("SELECT * FROM session")
    fun getAll(): Flowable<List<SessionWithRelations>>

    @Query("SELECT * FROM session WHERE id LIKE :id")
    fun getById(id: String): Flowable<SessionWithRelations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg sessions: Session)

    @Delete
    fun delete(session: Session)

}