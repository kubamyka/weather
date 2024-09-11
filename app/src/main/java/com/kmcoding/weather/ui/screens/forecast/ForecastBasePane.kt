package com.kmcoding.weather.ui.screens.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.kmcoding.weather.R
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.domain.model.Location
import com.kmcoding.weather.ui.ContentWithLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastBasePane(
    location: Location,
    navigateBack: () -> Unit,
    viewModel: ForecastViewModel = hiltViewModel(),
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val showNavigationIcon = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED

    val forecast by viewModel.forecast.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = location) {
        viewModel.fetchForecast(location)
    }

    ContentWithLoader(viewModel = viewModel, content = {
        Scaffold(topBar = {
            TopAppBar(
                colors =
                    topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                title = {
                    Text(
                        text = location.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    if (showNavigationIcon) {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back),
                            )
                        }
                    }
                },
            )
        }) { innerPadding ->
            Column(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()).fillMaxWidth(),
            ) {
                val currentWeather = forecast?.current
                if (currentWeather != null) {
                    HeadText(text = stringResource(id = R.string.current_weather))
                    ForecastCurrentDetails(
                        currentWeather = currentWeather,
                    )
                }

                val forecastObj = forecast?.forecast?.forecastObj
                if (!forecastObj.isNullOrEmpty()) {
                    val forecastHours = forecastObj[0].hours
                    HeadText(text = stringResource(id = R.string.weather_whole_day))
                    ForecastNextHoursDetails(
                        forecastHours = forecastHours,
                    )
                }
            }
        }
    })
}

@Composable
fun HeadText(text: String) {
    Column {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 1.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun LocationBasePanePreview() {
    ForecastBasePane(location = fakeLocations[0], navigateBack = {})
}
