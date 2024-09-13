package com.kmcoding.weather.fake

import com.kmcoding.weather.data.source.FakeDataSource.fakeForecast
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.flow

class FakeWeatherRepositoryImpl : WeatherRepository {
    override suspend fun getLocations(query: String) =
        flow {
            emit(fakeLocations)
        }

    override suspend fun getForecast(url: String) =
        flow {
            emit(fakeForecast)
        }
}
