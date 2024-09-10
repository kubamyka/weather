package com.kmcoding.weather.data.repository

import com.kmcoding.weather.data.remote.WeatherApi
import com.kmcoding.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl
    @Inject
    constructor(
        private val api: WeatherApi,
    ) : WeatherRepository
