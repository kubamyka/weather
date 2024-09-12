package com.kmcoding.weather.ui.screens.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kmcoding.weather.R
import com.kmcoding.weather.data.source.FakeDataSource.fakeWeathers
import com.kmcoding.weather.domain.model.CurrentWeather

@Composable
fun ForecastCurrentDetails(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 32.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.onPrimary)) {
            Text(
                text = currentWeather.condition?.text ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = currentWeather.getConditionIconUrl(),
                    error = painterResource(id = R.drawable.ic_no_photo),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier =
                        Modifier
                            .size(128.dp)
                            .padding(end = 16.dp),
                )
                Column {
                    TemperatureText(value = currentWeather.celsiusTemperature)
                    Text(
                        text = stringResource(R.string.wind, currentWeather.windKph),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(R.string.humidity, currentWeather.humidity),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = stringResource(R.string.clouds, currentWeather.cloud),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(text = currentWeather.lastUpdated, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastCurrentDetailsPreview() {
    ForecastCurrentDetails(currentWeather = fakeWeathers[0])
}
