package dev.jx.composeweather.data.remote.model.openweather

import com.google.gson.annotations.SerializedName

data class DailyWeather(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("moonrise")
    val moonrise: Int,
    @SerializedName("moonset")
    val moonset: Int,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("weather")
    val weather: List<WeatherD>,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("uvi")
    val uvi: Double
) {
    companion object {
        val default = DailyWeather(
            dt = 1637244000,
            sunrise = 1637222414,
            moonrise = 1637268420,
            moonset = 1637220420,
            moonPhase = 0.48,
            temp = Temp(
                day = 30.04,
                min = 21.45,
                max = 30.04,
                night = 21.45,
                evening = 25.36,
                morning = 22.2
            ),
            feelsLike = FeelsLike(
                day = 31.43,
                night = 22.13,
                evening = 25.67,
                morning = 22.49
            ),
            pressure = 1009,
            humidity = 52,
            dewPoint = 18.49,
            windSpeed = 4.12,
            windDeg = 194,
            windGust = 6.62,
            weather = listOf(
                WeatherD(
                    id = 501,
                    main = "Rain",
                    description = "moderate rain",
                    icon = "10d"
                )
            ),
            clouds = 96,
            pop = 0.98,
            rain = 6.01,
            uvi = 13.53
        )
    }
}

class WeatherD(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Temp(
    @SerializedName("day")
    val day: Double,
    @SerializedName("min")
    val min: Double,
    @SerializedName("max")
    val max: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("eve")
    val evening: Double,
    @SerializedName("morn")
    val morning: Double
)

class FeelsLike(
    @SerializedName("day")
    val day: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("eve")
    val evening: Double,
    @SerializedName("morn")
    val morning: Double
)
