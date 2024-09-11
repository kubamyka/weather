package com.kmcoding.weather.domain.model

data class Condition(
    val text: String,
    val icon: String,
) {
    fun getIconUrl(): String = "https:$icon"
}
