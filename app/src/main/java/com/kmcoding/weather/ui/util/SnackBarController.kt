package com.kmcoding.weather.ui.util

import com.kmcoding.weather.domain.model.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarEvent(
    val message: UiText,
    val action: SnackBarAction? = null,
)

data class SnackBarAction(
    val name: String,
    val action: suspend () -> Unit,
)

object SnackBarController {
    private val _events = Channel<SnackBarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _events.send(event)
    }
}
