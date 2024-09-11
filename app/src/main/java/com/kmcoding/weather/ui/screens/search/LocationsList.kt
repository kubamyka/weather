package com.kmcoding.weather.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmcoding.weather.R
import com.kmcoding.weather.domain.model.Location

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun LocationsListPane(
    modifier: Modifier = Modifier,
    onSelectedLocation: (Location) -> Unit,
    navigator: ThreePaneScaffoldNavigator<Long> = rememberListDetailPaneScaffoldNavigator<Long>(),
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val locations by viewModel.locations.collectAsStateWithLifecycle()
    val isSearchActive by viewModel.isSearchActive.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    Box {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                LocationsSearchBar(
                    query = query,
                    onQueryChange = viewModel::updateQuery,
                    onQuerySearch = viewModel::fetchLocations,
                    isSearchActive = isSearchActive,
                    toggleSearchActive = viewModel::toggleSearchActive,
                )
            }
            if (locations.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(48.dp).verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = stringResource(id = R.string.empty_locations), textAlign = TextAlign.Center)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(key = { location ->
                        location.id
                    }, items = locations) {
                        LocationItem(location = it, navigateToDetails = { location ->
                            onSelectedLocation(location)
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, location.id.toLong())
                        })
                    }
                }
            }
        }
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
