package com.kmcoding.weather.domain.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("forecastday")
    val forecastObj: List<ForecastObj>,
)
