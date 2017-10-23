package com.arthurnagy.droidcon.model

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = arrayOf(
        ForeignKey(entity = Session::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("session_id"),
                onDelete = CASCADE)))
data class Term @Ignore constructor(
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String,
        @PrimaryKey
        @SerializedName("tid") val id: Int) {

    @ColumnInfo(name = "session_id")
    var sessionId: String? = null

    constructor(name: String, url: String, id: Int, sessionId: String) : this(name, url, id) {
        this.sessionId = sessionId
    }

}