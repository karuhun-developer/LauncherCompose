package com.karuhun.feature.weather.data.repository

import android.util.Log
import com.karuhun.core.common.Resource
import com.karuhun.core.common.map
import com.karuhun.core.domain.repository.WeatherRepository
import com.karuhun.core.model.Weather
import com.karuhun.core.network.BuildConfig
import com.karuhun.core.network.safeApiCall
import com.karuhun.feature.weather.data.source.WeatherApiService
import com.karuhun.feature.weather.data.source.remote.response.toDomain
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository{
    override suspend fun getWeather(): Resource<Weather> {
        return safeApiCall {
            val response = apiService.getWeather(
                city = BuildConfig.CITY,
                apiKey = BuildConfig.OPEN_WEATHER_API_KEY
            )
            Log.d("TAG", "getWeather: $response")
            response
        }.map {
            it.toDomain()
        }
    }
}