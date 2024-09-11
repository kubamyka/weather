package com.kmcoding.weather.domain.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ForecastHour(
    @SerializedName("time_epoch")
    val timestamp: Long,
    @SerializedName("time")
    val date: String,
    @SerializedName("temp_c")
    val celsiusTemperature: Double,
    val condition: Condition?,
    @SerializedName("wind_kph")
    val windKph: Double,
    val humidity: Int,
    val cloud: Int,
) {
    fun getConditionIconUrl(): String = condition?.getIconUrl() ?: ""

    fun getOnlyTimeFromDate(): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formattedDate = LocalDateTime.parse(date, dateFormatter)
        return DateTimeFormatter.ofPattern("HH:mm").format(formattedDate)
    }
}
