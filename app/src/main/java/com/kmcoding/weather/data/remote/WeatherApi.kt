package com.kmcoding.weather.data.remote

import com.kmcoding.weather.domain.model.CurrentWeather
import com.kmcoding.weather.domain.model.Location
import retrofit2.http.GET

interface WeatherApi {
    @GET("v1/search.json")
    suspend fun getLocations(): List<Location>

    @GET("v1/current.json")
    suspend fun getCurrentWeather(): CurrentWeather
}
