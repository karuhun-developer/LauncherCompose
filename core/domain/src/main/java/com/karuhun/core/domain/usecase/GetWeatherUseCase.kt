package com.karuhun.core.domain.usecase

import com.karuhun.core.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke() = weatherRepository.getWeather()
}