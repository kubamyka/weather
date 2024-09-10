package com.kmcoding.weather.data.repository

import com.kmcoding.weather.data.remote.WeatherApi
import com.kmcoding.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val api: WeatherApi,
        private val weatherApiKey: String,
        private val weatherLang: String,
    ) : WeatherRepository {
        override suspend fun getLocations(query: String) =
            flow {
                emit(api.getLocations(query = query, weatherApiKey = weatherApiKey, weatherLang = weatherLang))
            }

        override suspend fun getCurrentWeather() =
            flow {
                emit(api.getCurrentWeather())
            }
    }
