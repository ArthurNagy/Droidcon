package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.model.Speaker

@Entity(tableName = Constants.TABLE_SESSION_SPEAKER_RELATION,
        primaryKeys = arrayOf(Constants.ID_SESSION, Constants.ID_SPEAKER),
        foreignKeys = arrayOf(ForeignKey(
                entity = Session::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf(Constants.ID_SESSION)
        ), ForeignKey(
                entity = Speaker::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf(Constants.ID_SPEAKER)
        )))
data class SessionSpeaker(
        @ColumnInfo(name = Constants.ID_SESSION, index = true)
        val sessionId: Int,
        @ColumnInfo(name = Constants.ID_SPEAKER, index = true)
        val speakerId: Int
)
