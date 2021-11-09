package dev.jx.composeweather.data.remote.model.openweather

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("weather")
    val weather: List<Weather?>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: Int
) {
    companion object {
        val default = CurrentWeather(
            coord = Coord(-22.90, -43.21),
            weather = listOf(),
            base = "stations",
            main = Main(25.00, 25.00, 25.00, 25.00, 0, 0),
            visibility = 10000,
            wind = Wind(170, 5.5),
            rain = null,
            clouds = Clouds(75),
            dt = 1636394628,
            sys = Sys("BR", 8429, 1636358626, 1636405768, 1),
            timezone = -10800,
            id = 3451190,
            name = "Rio de Janeiro",
            cod = 200
        )
    }
}