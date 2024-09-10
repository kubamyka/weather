package com.kmcoding.weather.data.repository

import com.kmcoding.weather.data.remote.WeatherApi
import com.kmcoding.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val api: WeatherApi,
    ) : WeatherRepository {
        override suspend fun getLocations() =
            flow {
                emit(api.getLocations())
            }

        override suspend fun getCurrentWeather() =
            flow {
                emit(api.getCurrentWeather())
            }
    }
