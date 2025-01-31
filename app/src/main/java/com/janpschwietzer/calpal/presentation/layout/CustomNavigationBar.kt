package com.janpschwietzer.calpal.presentation.layout

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.navigation.Screen

@Composable
fun CustomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
    ) {
        NavigationBarItem(
            selected = navController.currentDestination?.route == "dashboard",
            onClick = { navController.navigate(Screen.Dashboard.route) { launchSingleTop = true } },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.dashboard_title)
                )
            },
            label = { Text(stringResource(R.string.dashboard_title)) },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                selectedIconColor = MaterialTheme.colorScheme.onBackground,
                unselectedTextColor = Color.Gray,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color.Transparent,
            )
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "overview",
            onClick = { navController.navigate(Screen.Overview.route) { launchSingleTop = true } },
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.overview_title)
                )
            },
            label = { Text(stringResource(R.string.overview_title)) },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                selectedIconColor = MaterialTheme.colorScheme.onBackground,
                unselectedTextColor = Color.Gray,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color.Transparent,
            )
        )
    }
}