package com.kmcoding.weather.ui.screens

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class LoaderViewModel : BaseViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected fun setLoading(loading: Boolean) {
        _isLoading.update { loading }
    }
}
