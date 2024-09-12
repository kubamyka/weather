package com.kmcoding.weather.ui.screens.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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

    val showRetry by viewModel.showRetry.collectAsStateWithLifecycle()
    val forecast by viewModel.forecast.collectAsStateWithLifecycle()
    var lastId: Int? by rememberSaveable { mutableStateOf(null) }

    if (lastId != location.id) {
        lastId = location.id
        LaunchedEffect(key1 = location.id) {
            viewModel.fetchForecast(location)
        }
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
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
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

                if (showRetry) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Text(
                                text = stringResource(id = R.string.error_forecast_not_fetched),
                                modifier = Modifier.padding(horizontal = 48.dp, vertical = 16.dp),
                                textAlign = TextAlign.Center,
                            )
                            Button(onClick = {
                                viewModel.setShowRetry(false)
                                viewModel.fetchForecast(location)
                            }) {
                                Text(text = stringResource(id = R.string.retry))
                            }
                        }
                    }
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
