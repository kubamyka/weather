package com.kmcoding.weather.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.kmcoding.weather.data.source.FakeDataSource.fakeWeathers
import com.kmcoding.weather.domain.model.CurrentWeather

@Composable
fun WeatherDetailsPane(currentWeather: CurrentWeather) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = currentWeather.condition.icon,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDetailPanePreview() {
    WeatherDetailsPane(currentWeather = fakeWeathers[0])
}
