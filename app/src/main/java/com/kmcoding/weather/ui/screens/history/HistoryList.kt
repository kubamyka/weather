package com.kmcoding.weather.ui.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.kmcoding.weather.ui.screens.search.LocationItem

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HistoryListPane(
    modifier: Modifier = Modifier,
    onSelectedLocation: (Location) -> Unit,
    navigator: ThreePaneScaffoldNavigator<Long> = rememberListDetailPaneScaffoldNavigator<Long>(),
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val locations by viewModel.locations.collectAsStateWithLifecycle()
    Box(modifier = modifier) {
        if (locations.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(48.dp).verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(id = R.string.empty_history), textAlign = TextAlign.Center)
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(key = { location ->
                        location.id
                    }, items = locations) {
                        LocationItem(location = it, navigateToDetails = { location ->
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, location.id.toLong())
                            onSelectedLocation(location)
                        })
                    }
                    item {
                        Box(modifier = Modifier.padding(32.dp))
                    }
                }
                Button(
                    onClick = viewModel::clearHistory,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp),
                ) {
                    Text(text = stringResource(id = R.string.clear_history))
                }
            }
        }
    }
}
