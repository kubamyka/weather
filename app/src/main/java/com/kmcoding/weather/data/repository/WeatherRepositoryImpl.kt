package com.kmcoding.weather.data.repository

import com.kmcoding.weather.data.db.LocationDao
import com.kmcoding.weather.data.remote.WeatherApi
import com.kmcoding.weather.domain.model.Location
import com.kmcoding.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val locationDao: LocationDao,
        private val api: WeatherApi,
        private val weatherApiKey: String,
        private val weatherLang: String,
    ) : WeatherRepository {
        override suspend fun getLocations(query: String): Flow<List<Location>> {
            val locations =
                api.getLocations(query = query, weatherApiKey = weatherApiKey, weatherLang = weatherLang)

            locations.forEach {
                it.apply { timestamp = System.currentTimeMillis() }
            }

            withContext(Dispatchers.IO) { locationDao.insertAll(locations) }
            return flow { emit(locations) }
        }

        override suspend fun getForecast(url: String) =
            flow {
                emit(
                    api.getForecast(
                        query = url,
                        weatherApiKey = weatherApiKey,
                        weatherLang = weatherLang,
                    ),
                )
            }
    }
