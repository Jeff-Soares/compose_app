package dev.jx.composeweather.data.remote

import dev.jx.composeweather.data.remote.model.openweather.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("weather")
    suspend fun getCurrentlyWeather(
        @Query("q") city: String,
        @Query("appid") key: String,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeather>

    @GET("onecall")
    suspend fun getDailyWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeather> // TODO Create specific class

}