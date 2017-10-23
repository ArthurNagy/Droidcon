package com.arthurnagy.droidcon.model

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = Session::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("session_id"),
                onDelete = CASCADE)))
data class Speaker @Ignore constructor(
        @PrimaryKey
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String) {

    @ColumnInfo(name = "session_id")
    var sessionId: String? = null

    constructor(name: String, url: String, sessionId: String) : this(name, url) {
        this.sessionId = sessionId
    }
}