package dev.jx.composeweather.data.remote.model.openweather

import com.google.gson.annotations.SerializedName

data class HourlyWeather(
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dew_point: Double,
    @SerializedName("evi")
    val uvi: Double,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    val weather: List<WeatherH>,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: Rain
) {
    companion object {
        val default = listOf(
            HourlyWeather(
                dt = 1637262000,
                temp = 25.21,
                feelsLike = 25.59,
                pressure = 1012,
                humidity = 69,
                dew_point = 19.12,
                uvi = 0.19,
                clouds = 78,
                visibility = 10000,
                windSpeed = 3.78,
                windDeg = 247,
                weather = listOf(
                    WeatherH(
                        id = 500,
                        main = "Rain",
                        description = "light rain",
                        icon = "10d"
                    )
                ),
                pop = 0.67,
                rain = Rain(oneHour = 0.66)
            )
        )
    }
}

data class WeatherH(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)
