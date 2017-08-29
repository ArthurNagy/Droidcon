package com.arthurnagy.droidconberlin.model

import com.google.gson.annotations.SerializedName


data class Term(
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String,
        @SerializedName("tid") val id: Int
)