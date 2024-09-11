package com.kmcoding.weather.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.kmcoding.weather.R

enum class NavDestination(
    @StringRes
    val labelRes: Int,
    val icon: ImageVector,
) {
    SEARCH(R.string.menu_search, Icons.Default.Search),
    HISTORY(R.string.menu_history, Icons.Default.Refresh),
}
