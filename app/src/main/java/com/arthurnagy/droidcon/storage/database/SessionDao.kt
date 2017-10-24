package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.*
import com.arthurnagy.droidcon.model.Session
import io.reactivex.Single

@Dao
interface SessionDao {

    @Query("SELECT * FROM ${Constants.TABLE_SESSION}")
    fun getAll(): Single<List<Session>>

    @Query("SELECT * FROM ${Constants.TABLE_SESSION} WHERE id LIKE :id")
    fun getById(id: String): Single<Session>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg sessions: Session): Array<Long>

    @Delete
    fun delete(session: Session): Int

}