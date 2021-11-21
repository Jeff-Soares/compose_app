package dev.jx.composeweather.data.remote.model.openweather

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val oneHour: Double
)
