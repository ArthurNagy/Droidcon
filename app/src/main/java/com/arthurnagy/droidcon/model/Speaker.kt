package com.arthurnagy.droidcon.model

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import com.arthurnagy.droidcon.storage.database.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.TABLE_SPEAKER)
data class Speaker(
        @PrimaryKey
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String)