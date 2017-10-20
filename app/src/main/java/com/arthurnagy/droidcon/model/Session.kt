package com.arthurnagy.droidcon.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Session(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("url") val url: String,
        @SerializedName("room") val room: Room,
        @SerializedName("speakers") val speakers: List<Speaker>?,
        @SerializedName("terms") val terms: List<Term>?,
        @SerializedName("start") val startDate: Date,
        @SerializedName("end") val endDate: Date,
        @SerializedName("description") val description: String?,
        var isSaved: Boolean = false) {
    enum class Room {
        @SerializedName("3518")
        LAMARR,
        @SerializedName("3519")
        LOVELACE,
        @SerializedName("3520")
        TURING,
        @SerializedName("3521")
        ZUSE
    }

    companion object {
        private const val INTERMISSION = "_intermission"

        fun isIntermission(session: Session?) = session?.id?.contains(Session.INTERMISSION) != false
    }
}