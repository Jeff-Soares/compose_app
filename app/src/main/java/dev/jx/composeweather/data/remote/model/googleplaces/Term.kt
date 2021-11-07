package dev.jx.composeweather.data.remote.model.googleplaces


import com.google.gson.annotations.SerializedName

data class Term(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("value")
    val value: String
)