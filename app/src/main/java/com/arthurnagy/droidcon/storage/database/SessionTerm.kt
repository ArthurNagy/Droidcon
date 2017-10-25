package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.model.Term

@Entity(tableName = Constants.TABLE_SESSION_TERM_RELATION,
        primaryKeys = arrayOf(Constants.ID_SESSION, Constants.ID_TERM),
        foreignKeys = arrayOf(ForeignKey(
                entity = Session::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf(Constants.ID_SESSION),
                onDelete = ForeignKey.CASCADE
        ), ForeignKey(
                entity = Term::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf(Constants.ID_TERM),
                onDelete = ForeignKey.CASCADE
        )))
data class SessionTerm(
        @ColumnInfo(name = Constants.ID_SESSION, index = true)
        val sessionId: String,
        @ColumnInfo(name = Constants.ID_TERM, index = true)
        val termId: Int
)