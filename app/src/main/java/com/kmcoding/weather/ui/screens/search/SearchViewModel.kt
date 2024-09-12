package com.kmcoding.weather.ui.screens.search

import com.kmcoding.weather.R
import com.kmcoding.weather.domain.model.Location
import com.kmcoding.weather.domain.model.UiText
import com.kmcoding.weather.domain.repository.WeatherRepository
import com.kmcoding.weather.ui.screens.LoaderViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
    ) : LoaderViewModel() {
        private val _isSearchActive = MutableStateFlow(false)
        val isSearchActive = _isSearchActive.asStateFlow()

        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        private val _locations = MutableStateFlow<List<Location>>(listOf())
        val locations = _locations.asStateFlow()

        fun toggleSearchActive() {
            updateQuery("")
            _isSearchActive.update { !it }
        }

        fun updateQuery(query: String) {
            _searchQuery.update { query }
        }

        private fun updateLocations(locations: List<Location>) {
            _locations.update { locations }
        }

        fun isQueryValidate(): Boolean {
            val regex = Regex("(?<!\\S)\\p{Alpha}+(?!\\S)")
            return regex.matches(_searchQuery.value)
        }

        fun fetchLocations() {
            launchDataLoad {
                if (_searchQuery.value.isEmpty()) {
                    sendSnackBarMessage(UiText.StringResource(R.string.error_query_empty))
                    return@launchDataLoad
                }

                if (!isQueryValidate()) {
                    sendSnackBarMessage(UiText.StringResource(R.string.error_query_does_not_match))
                    return@launchDataLoad
                }

                weatherRepository
                    .getLocations(_searchQuery.value)
                    .catch { error ->
                        setLoading(false)
                        sendSnackBarError(error, R.string.error_download_locations)
                    }.map { list ->
                        list.sortedBy { it.name }
                    }.collect { list ->
                        setLoading(false)
                        updateLocations(list)

                        if (list.isEmpty()) {
                            sendSnackBarMessage(UiText.StringResource(R.string.error_locations_not_found))
                        }
                    }
            }
        }
    }
