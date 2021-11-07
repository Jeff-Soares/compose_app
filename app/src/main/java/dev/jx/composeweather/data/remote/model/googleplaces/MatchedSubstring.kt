package dev.jx.composeweather.data.remote.model.googleplaces


import com.google.gson.annotations.SerializedName

data class MatchedSubstring(
    @SerializedName("length")
    val length: Int,
    @SerializedName("offset")
    val offset: Int
)