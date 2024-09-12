package com.kmcoding.weather.ui.screens.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kmcoding.weather.R
import com.kmcoding.weather.data.source.FakeDataSource.fakeHours
import com.kmcoding.weather.domain.model.ForecastHour

@Composable
fun ForecastNextHoursDetails(
    modifier: Modifier = Modifier,
    forecastHours: List<ForecastHour> = listOf(),
) {
    LazyRow{
        items(key = { hour ->
            hour.timestamp
        }, items = forecastHours) {
            ForecastHourItem(hour = it)
        }
    }
}

@Composable
fun ForecastHourItem(hour: ForecastHour) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier =
            Modifier
                .wrapContentHeight(align = Alignment.Top)
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary),
        ) {
            Text(
                text = hour.getOnlyTimeFromDate(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            AsyncImage(
                model = hour.getConditionIconUrl(),
                error = painterResource(id = R.drawable.ic_no_photo),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(72.dp),
            )
            TemperatureText(
                value = hour.celsiusTemperature,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastHourItemPreview() {
    ForecastHourItem(hour = fakeHours[0])
}
