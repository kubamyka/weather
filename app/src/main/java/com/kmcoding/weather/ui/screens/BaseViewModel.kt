package com.kmcoding.weather.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmcoding.weather.domain.model.UiText
import com.kmcoding.weather.ui.util.SnackBarController
import com.kmcoding.weather.ui.util.SnackBarEvent
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    /**
     * Sending globally snackBar error.
     * @param error throwable error to display
     * @param defaultError string resource default error in case of error's localized message is null
     */
    protected suspend fun sendSnackBarError(
        error: Throwable,
        defaultError: Int,
    ) {
        val message = error.localizedMessage
        val uiText =
            if (message == null) {
                UiText.StringResource(defaultError)
            } else {
                UiText.DynamicString(message)
            }
        sendSnackBarMessage(uiText)
    }

    /**
     * Sending globally snackBar message.
     * @param uiText dynamic or string resource message to send
     */
    protected suspend fun sendSnackBarMessage(uiText: UiText) {
        viewModelScope.launch {
            SnackBarController.sendEvent(
                event =
                    SnackBarEvent(
                        message = uiText,
                    ),
            )
        }
    }
}
