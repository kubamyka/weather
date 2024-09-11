package com.kmcoding.weather.ui.screens.forecast

import androidx.lifecycle.viewModelScope
import com.kmcoding.weather.R
import com.kmcoding.weather.domain.model.Forecast
import com.kmcoding.weather.domain.model.Location
import com.kmcoding.weather.domain.repository.WeatherRepository
import com.kmcoding.weather.ui.screens.LoaderViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
    ) : LoaderViewModel() {
        private val _forecast = MutableStateFlow<Forecast?>(null)
        var forecast = _forecast.asStateFlow()

        private fun updateForecast(forecast: Forecast) {
            _forecast.update { forecast }
        }

        fun fetchForecast(location: Location) {
            viewModelScope.launch {
                // updateForecast(fakeForecast)
                weatherRepository
                    .getForecast(location.url)
                    .catch { error ->
                        setLoading(false)
                        sendSnackBarError(error, R.string.error_download_forecast)
                    }.collect { forecast ->
                        setLoading(false)
                        updateForecast(forecast)
                    }
            }
        }
    }
