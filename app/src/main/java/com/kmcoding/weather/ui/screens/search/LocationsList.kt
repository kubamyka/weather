package com.kmcoding.weather.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kmcoding.weather.R
import com.kmcoding.weather.domain.model.Location

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LocationsListPane(
    navigateToDetails: (Location) -> Unit,
    onQueryChange: (String) -> Unit,
    onQuerySearch: () -> Unit,
    toggleSearchActive: () -> Unit,
    pullRefreshState: PullRefreshState,
    modifier: Modifier = Modifier,
    locations: List<Location> = listOf(),
    isSearchActive: Boolean = false,
    isLoading: Boolean = false,
    query: String = "",
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            LocationsSearchBar(
                query = query,
                onQueryChange = { onQueryChange(it) },
                onQuerySearch = { onQuerySearch() },
                isSearchActive = isSearchActive,
                toggleSearchActive = { toggleSearchActive() },
            )
        }
        if (locations.isEmpty()) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(48.dp)
                        .pullRefresh(pullRefreshState)
                        .verticalScroll(rememberScrollState()),
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
                    LocationItem(location = it, navigateToDetails = navigateToDetails)
                }
            }
        }

        /*PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )*/
    }
}
