package com.kmcoding.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmcoding.weather.ui.screens.LoaderViewModel

@Composable
fun ContentWithLoader(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
    viewModel: LoaderViewModel,
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    Box(modifier = modifier) {
        content()

        if (!isLoading) return

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f)),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(48.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}
