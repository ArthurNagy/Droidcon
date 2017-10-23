package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.*
import com.arthurnagy.droidcon.model.Session
import io.reactivex.Single

@Dao
interface SessionDao {

    @Query("SELECT * FROM Session")
    fun getAll(): Single<List<SessionWithRelations>>

    @Query("SELECT * FROM session WHERE id LIKE :id")
    fun getById(id: String): Single<SessionWithRelations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg sessions: Session): Array<Long>

    @Delete
    fun delete(session: Session): Int

}