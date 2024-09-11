package com.kmcoding.weather.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import com.kmcoding.weather.R
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.domain.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationBasePane(
    location: Location,
    navigateBack: () -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val showNavigationIcon = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED

    Scaffold(topBar = {
        TopAppBar(
            colors =
                topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
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
            modifier = Modifier.padding(innerPadding).fillMaxSize().verticalScroll(rememberScrollState()),
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun LocationBasePanePreview() {
    LocationBasePane(location = fakeLocations[0], navigateBack = {})
}
