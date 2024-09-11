package com.kmcoding.weather.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmcoding.weather.R

@Composable
fun LocationsSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onQuerySearch: () -> Unit,
    isSearchActive: Boolean = false,
    toggleSearchActive: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .height(56.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(24.dp))
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                ).clickable(enabled = true) {
                    toggleSearchActive()
                },
    ) {
        if (isSearchActive) {
            TextField(
                value = query,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_query_here),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                },
                textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                onValueChange = onQueryChange,
                maxLines = 1,
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            onQuerySearch()
                        },
                    ),
                modifier = Modifier.weight(1f),
            )
        } else {
            Text(
                text = stringResource(id = R.string.search_locations),
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        IconButton(onClick = toggleSearchActive) {
            Icon(
                imageVector = if (isSearchActive) Icons.Rounded.Close else Icons.Rounded.Search,
                contentDescription =
                    if (isSearchActive) {
                        stringResource(R.string.close)
                    } else {
                        stringResource(
                            R.string.search,
                        )
                    },
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
