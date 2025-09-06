package com.karuhun.core.domain.repository

import com.karuhun.core.common.Resource
import com.karuhun.core.model.Weather

interface WeatherRepository {
    suspend fun getWeather(): Resource<Weather>
}