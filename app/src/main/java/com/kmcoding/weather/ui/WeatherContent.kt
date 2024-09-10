package com.kmcoding.weather.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.kmcoding.weather.ui.screens.search.SearchViewModel

private val WINDOW_WIDTH_LARGE = 1200.dp

@Composable
fun WeatherApp() {
    WeatherNavigationWrapperUI {
        WeatherAppContent()
    }
}

@Composable
private fun WeatherNavigationWrapperUI(content: @Composable () -> Unit = {}) {
    val selectedDestination: NavDestination by remember {
        mutableStateOf(NavDestination.Search)
    }

    val windowSize =
        with(LocalDensity.current) {
            currentWindowSize().toSize().toDpSize()
        }
    val navLayoutType =
        if (windowSize.width >= WINDOW_WIDTH_LARGE) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())
        }

    NavigationSuiteScaffold(navigationSuiteItems = {
        NavDestination.entries.forEach {
            item(
                label = { Text(stringResource(it.labelRes)) },
                icon = { Icon(it.icon, stringResource(it.labelRes)) },
                selected = it == selectedDestination,
                onClick = { /*TODO update selection*/ },
            )
        }
    }, layoutType = navLayoutType, contentColor = Color.Red) {
        content()
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterialApi::class)
@Composable
fun WeatherAppContent(viewModel: SearchViewModel = hiltViewModel()) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    LaunchedEffect(key1 = snackBarHostState) {
        viewModel.snackBarChannel.collect { message ->
            snackBarHostState.showSnackbar(message = message.asString(context))
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }) { innerPadding ->
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {},
            detailPane = {
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}
