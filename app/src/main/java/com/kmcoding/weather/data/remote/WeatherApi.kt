package com.kmcoding.weather.data.remote

import com.kmcoding.weather.domain.model.CurrentWeather
import com.kmcoding.weather.domain.model.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/search.json")
    suspend fun getLocations(
        @Query("q")
        query: String,
        @Query("key")
        weatherApiKey: String,
        @Query("lang")
        weatherLang: String,
    ): List<Location>

    @GET("v1/current.json")
    suspend fun getCurrentWeather(): CurrentWeather
}
