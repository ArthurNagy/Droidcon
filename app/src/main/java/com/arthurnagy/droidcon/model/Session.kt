package com.arthurnagy.droidcon.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Session constructor(
        @PrimaryKey
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("url") val url: String,
        @SerializedName("room") val room: Room,
        @SerializedName("start") val startDate: Date,
        @SerializedName("end") val endDate: Date,
        @SerializedName("description") val description: String?,
        var isSaved: Boolean = false) {

    @Ignore
    @SerializedName("speakers")
    var speakers: List<Speaker>? = null
    @Ignore
    @SerializedName("terms")
    var terms: List<Term>? = null

    constructor(id: String, title: String, url: String, room: Room, startDate: Date, endDate: Date, description: String?, isSaved: Boolean,
                speakers: List<Speaker>?,
                terms: List<Term>?) : this(id, title, url, room, startDate, endDate, description, isSaved) {
        this.speakers = speakers
        this.terms = terms
    }

    enum class Room(val roomValue: String) {
        @SerializedName("3518")
        LAMARR("3518"),
        @SerializedName("3519")
        LOVELACE("3519"),
        @SerializedName("3520")
        TURING("3520"),
        @SerializedName("3521")
        ZUSE("3521")
    }

    companion object {
        private const val INTERMISSION = "_intermission"

        fun isIntermission(session: Session?) = session?.id?.contains(Session.INTERMISSION) != false
    }
}