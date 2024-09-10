package com.kmcoding.weather.domain.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val celsiusTemperature: Double,
    val condition: Condition,
    @SerializedName("wind_kph")
    val windKph: Double,
    val humidity: Int,
    val cloud: Int,
)
