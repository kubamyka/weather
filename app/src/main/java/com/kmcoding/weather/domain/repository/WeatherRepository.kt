package com.kmcoding.weather.domain.repository

import com.kmcoding.weather.domain.model.Forecast
import com.kmcoding.weather.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getLocations(query: String): Flow<List<Location>>

    suspend fun getForecast(url: String): Flow<Forecast>
}
