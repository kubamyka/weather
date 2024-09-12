package com.kmcoding.weather.ui.screens

import androidx.lifecycle.viewModelScope
import com.kmcoding.weather.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class LoaderViewModel : BaseViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected fun setLoading(loading: Boolean) {
        _isLoading.update { loading }
    }

    protected fun launchDataLoad(block: suspend () -> Unit): Job =
        viewModelScope.launch {
            try {
                setLoading(true)
                block()
            } catch (cause: Exception) {
                sendSnackBarError(cause, R.string.error_unknown)
            } finally {
                setLoading(false)
            }
        }
}
