package com.kmcoding.weather.ui.screens.forecast

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TemperatureText(
    value: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
) {
    val color =
        when {
            value < 10 -> Color.Blue
            value in 10.0..20.0 -> Color.Black
            else -> Color.Red
        }
    Text(text = "$value°C", style = style, color = color, modifier = modifier)
}
