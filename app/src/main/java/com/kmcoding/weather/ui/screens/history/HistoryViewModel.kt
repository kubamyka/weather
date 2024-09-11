package com.kmcoding.weather.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmcoding.weather.data.db.LocationDao
import com.kmcoding.weather.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
    @Inject
    constructor(
        private val locationDao: LocationDao,
    ) : ViewModel() {
        private val _locations = MutableStateFlow<List<Location>>(listOf())
        val locations =
            locationDao.getHistoryLocations().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = _locations.value,
            )

        fun clearHistory() {
            viewModelScope.launch {
                locationDao.deleteAllLocations()
            }
        }
    }
