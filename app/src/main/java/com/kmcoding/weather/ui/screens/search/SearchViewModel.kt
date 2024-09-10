package com.kmcoding.weather.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmcoding.weather.R
import com.kmcoding.weather.domain.model.Location
import com.kmcoding.weather.domain.model.UiText
import com.kmcoding.weather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
    ) : ViewModel() {
        private val _isLoading = MutableStateFlow(false)
        val isLoading = _isLoading.asStateFlow()

        private val _isSearchActive = MutableStateFlow(false)
        val isSearchActive = _isSearchActive.asStateFlow()

        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        private val _selectedLocation = MutableStateFlow<Location?>(null)
        val selectedLocation = _selectedLocation.asStateFlow()

        private val _snackBarChannel = Channel<UiText>()
        val snackBarChannel = _snackBarChannel.receiveAsFlow()

        private val _locations = MutableStateFlow<List<Location>>(listOf())
        val locations = _locations.asStateFlow()

        private fun setLoading(loading: Boolean) {
            _isLoading.update { loading }
        }

        fun toggleSearchActive() {
            updateQuery("")
            _isSearchActive.update { !it }
        }

        fun updateQuery(query: String) {
            _searchQuery.update { query }
        }

        fun updateSelectedLocation(location: Location) {
            _selectedLocation.update { location }
        }

        private fun updateLocations(locations: List<Location>) {
            _locations.update { locations }
        }

        fun fetchLocations() {
            setLoading(true)
            viewModelScope.launch {
                weatherRepository
                    .getLocations()
                    .catch { error ->
                        setLoading(false)
                        sendErrorMessage(error)
                    }.map { list ->
                        list.sortedBy { it.name }
                    }.collect { list ->
                        setLoading(false)
                        updateLocations(list)
                        updateSelectedLocation(_selectedLocation.value ?: list.first())
                    }
            }
        }

        private suspend fun sendErrorMessage(error: Throwable) {
            val message = error.localizedMessage
            val uiText =
                if (message == null) {
                    UiText.StringResource(R.string.error_download_locations)
                } else {
                    UiText.DynamicString(message)
                }
            _snackBarChannel.send(uiText)
        }
    }
