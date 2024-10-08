package com.kmcoding.weather.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kmcoding.weather.R
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.domain.model.Location

@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    location: Location,
    navigateToDetails: (Location) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { navigateToDetails(location) }
                    .background(color = MaterialTheme.colorScheme.onPrimary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = Icons.Rounded.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 16.dp).size(32.dp)
            )
            LocationContent(location = location)
        }
    }
}

@Composable
fun LocationContent(location: Location, modifier : Modifier = Modifier) {
    Column(modifier = modifier.padding(12.dp), horizontalAlignment = Alignment.Start) {
        Text(text = location.name, style = MaterialTheme.typography.titleLarge)
        Text(
            text = location.country,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationItemPreview() {
    LocationItem(location = fakeLocations[0], navigateToDetails = {})
}
