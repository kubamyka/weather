package com.kmcoding.weather.ui.screens.forecast

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
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
    ) : LoaderViewModel() {
        private val _forecast = MutableStateFlow<Forecast?>(null)
        var forecast = _forecast.asStateFlow()
        private val _showRetry = MutableStateFlow(false)
        val showRetry = _showRetry.asStateFlow()

        fun setShowRetry(showRetry: Boolean) {
            _showRetry.update { showRetry }
        }

        private fun updateForecast(forecast: Forecast) {
            _forecast.update { forecast }
        }

        fun fetchForecast(location: Location) {
            launchDataLoad {
                weatherRepository
                    .getForecast(location.url)
                    .catch { error ->
                        if (error is IOException) setShowRetry(true)
                        setLoading(false)
                        sendSnackBarError(error, R.string.error_download_forecast)
                    }.collect { forecast ->
                        setLoading(false)
                        updateForecast(forecast)
                    }
            }
        }
    }
