package com.karuhun.core.model

data class Weather(
    val icon: String?,
    val temp: Double?,
) {
    companion object {
        val Empty = Weather(
            icon = "",
            temp = null
        )
    }
}
