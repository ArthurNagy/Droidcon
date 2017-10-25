package com.arthurnagy.droidcon.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.arthurnagy.droidcon.storage.database.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.TABLE_SPEAKER)
data class Speaker(
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String)