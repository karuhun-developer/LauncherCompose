package com.karuhun.feature.weather.data.source

import com.karuhun.feature.weather.data.source.remote.response.GetWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): GetWeatherResponse
}