package com.arthurnagy.droidcon.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Speaker(
        @PrimaryKey
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String
)