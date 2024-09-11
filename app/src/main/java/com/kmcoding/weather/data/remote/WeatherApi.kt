package com.kmcoding.weather.data.remote

import com.kmcoding.weather.domain.model.Forecast
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

    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("q")
        query: String,
        @Query("key")
        weatherApiKey: String,
        @Query("lang")
        weatherLang: String,
    ): Forecast
}
