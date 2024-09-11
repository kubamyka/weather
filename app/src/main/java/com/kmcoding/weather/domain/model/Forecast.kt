package com.kmcoding.weather.domain.model

data class Forecast(
    val current: CurrentWeather?,
    val forecast: ForecastResponse?,
)
