package com.kmcoding.weather.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.kmcoding.weather.R
import com.kmcoding.weather.domain.model.Location
import com.kmcoding.weather.ui.screens.forecast.ForecastBasePane
import com.kmcoding.weather.ui.screens.history.HistoryListPane
import com.kmcoding.weather.ui.screens.search.LocationsListPane
import com.kmcoding.weather.ui.util.ObserveAsEvents
import com.kmcoding.weather.ui.util.SnackBarController
import kotlinx.coroutines.launch

private val WINDOW_WIDTH_LARGE = 1200.dp

@Composable
fun WeatherApp(
    windowSize: DpSize,
    currentWindowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    var selectedLocation by rememberSaveable { mutableStateOf<Location?>(null) }
    var selectedDestination: NavDestination by rememberSaveable {
        mutableStateOf(NavDestination.SEARCH)
    }

    val windowWidthSizeClass = currentWindowAdaptiveInfo.windowSizeClass.windowWidthSizeClass
    val isExpanded = windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
    val isLargeWidth = windowSize.width >= WINDOW_WIDTH_LARGE

    val navLayoutType =
        when {
            selectedLocation != null && !isExpanded -> NavigationSuiteType.None
            isLargeWidth -> NavigationSuiteType.NavigationDrawer
            else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo)
        }

    val myNavigationSuiteItemColors =
        NavigationSuiteDefaults.itemColors(
            navigationBarItemColors =
                NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onSecondary,
                ),
        )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavDestination.entries.forEach {
                item(
                    label = { Text(stringResource(it.labelRes)) },
                    icon = { Icon(it.icon, stringResource(it.labelRes)) },
                    selected = it == selectedDestination,
                    onClick = { selectedDestination = it },
                    colors = myNavigationSuiteItemColors,
                )
            }
        },
        navigationSuiteColors =
            NavigationSuiteDefaults.colors(
                navigationBarContainerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        layoutType = navLayoutType,
    ) {
        WeatherAppContent(
            selectedLocation = selectedLocation,
            onSelectedLocation = { location -> selectedLocation = location },
            selectedDestination = selectedDestination,
            listWidth = if (isLargeWidth) windowSize.width / 3 else windowSize.width / 2,
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun WeatherAppContent(
    selectedLocation: Location?,
    onSelectedLocation: (Location?) -> Unit,
    selectedDestination: NavDestination,
    listWidth: Dp,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler(navigator.canNavigateBack()) {
        onSelectedLocation(null)
        navigator.navigateBack()
    }

    ObserveAsEvents(
        flow = SnackBarController.events,
        snackBarHostState,
    ) { event ->
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()

            val result =
                snackBarHostState.showSnackbar(
                    message = event.message.asString(context),
                    actionLabel = event.action?.name,
                    duration = SnackbarDuration.Long,
                )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }) { innerPadding ->
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane(modifier = Modifier.preferredWidth(listWidth)) {
                    when (selectedDestination) {
                        NavDestination.SEARCH -> {
                            LocationsListPane(
                                navigator = navigator,
                                onSelectedLocation = onSelectedLocation,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }

                        NavDestination.HISTORY -> {
                            HistoryListPane(
                                navigator = navigator,
                                onSelectedLocation = onSelectedLocation,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {
                    if (selectedLocation != null) {
                        ForecastBasePane(
                            location = selectedLocation,
                            navigateBack = {
                                onSelectedLocation(null)
                                navigator.navigateBack()
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = stringResource(id = R.string.empty_forecast),
                                modifier = Modifier.padding(32.dp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            },
        )
    }
}
