package com.kmcoding.weather.domain.model

import com.google.gson.annotations.SerializedName

data class ForecastObj(
    val date: String,
    @SerializedName("date_epoch")
    val timestamp: Long,
    @SerializedName("hour")
    val hours: List<ForecastHour>,
)
