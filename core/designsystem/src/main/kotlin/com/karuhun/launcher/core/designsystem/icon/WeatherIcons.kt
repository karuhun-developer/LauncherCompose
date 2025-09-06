/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karuhun.launcher.core.designsystem.icon

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.karuhun.launcher.core.designsystem.R

/**
 * Weather Icons font family using Weather Icons font
 * Download from: https://erikflowers.github.io/weather-icons/
 */
val WeatherIconsFont = FontFamily(
    Font(R.font.core_designsystem_weathericons_regular_webfont, FontWeight.Normal)
)

/**
 * Maps OpenWeatherMap icon codes to Weather Icons font characters
 */
object WeatherIcons {

    // OpenWeatherMap icon codes to Weather Icons font characters mapping
    private val iconMap = mapOf(
        // Clear sky
        "01d" to "\uf00d", // wi-day-sunny
        "01n" to "\uf02e", // wi-night-clear

        // Few clouds
        "02d" to "\uf002", // wi-day-cloudy
        "02n" to "\uf081", // wi-night-alt-cloudy

        // Scattered clouds
        "03d" to "\uf013", // wi-cloud
        "03n" to "\uf013", // wi-cloud

        // Broken clouds
        "04d" to "\uf012", // wi-cloudy
        "04n" to "\uf012", // wi-cloudy

        // Shower rain
        "09d" to "\uf009", // wi-showers
        "09n" to "\uf009", // wi-showers

        // Rain
        "10d" to "\uf008", // wi-day-rain
        "10n" to "\uf028", // wi-night-rain

        // Thunderstorm
        "11d" to "\uf010", // wi-thunderstorm
        "11n" to "\uf010", // wi-thunderstorm

        // Snow
        "13d" to "\uf00a", // wi-snow
        "13n" to "\uf00a", // wi-snow

        // Mist
        "50d" to "\uf014", // wi-fog
        "50n" to "\uf014", // wi-fog
    )

    /**
     * Get weather icon character from OpenWeatherMap icon code
     */
    fun getWeatherIcon(iconCode: String?): String {
        return iconMap[iconCode] ?: "\uf07b" // wi-na (not available)
    }

    /**
     * Get weather icon character from OpenWeatherMap icon code with fallback
     */
    fun getWeatherIconOrDefault(iconCode: String?, default: String = "☀"): String {
        return iconMap[iconCode] ?: default
    }
}
