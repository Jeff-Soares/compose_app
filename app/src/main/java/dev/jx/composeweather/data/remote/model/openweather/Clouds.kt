package dev.jx.composeweather.data.remote.model.openweather


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)