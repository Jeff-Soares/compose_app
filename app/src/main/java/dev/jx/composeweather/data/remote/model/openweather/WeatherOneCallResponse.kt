package dev.jx.composeweather.data.remote.model.openweather

import com.google.gson.annotations.SerializedName

data class WeatherOneCallResponse(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("hourly")
    val hourly: List<HourlyWeather>,
    @SerializedName("daily")
    val daily: List<DailyWeather>,
)