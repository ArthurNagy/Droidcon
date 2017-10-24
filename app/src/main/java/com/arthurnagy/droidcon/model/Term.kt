package com.arthurnagy.droidcon.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.arthurnagy.droidcon.storage.database.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.TABLE_TERM)
data class Term (
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String,
        @PrimaryKey
        @SerializedName("tid") val id: Int)