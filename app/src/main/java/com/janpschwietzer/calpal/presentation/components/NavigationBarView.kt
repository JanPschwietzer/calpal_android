package com.janpschwietzer.calpal.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.janpschwietzer.calpal.R

@Composable
fun NavigationBarView(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == "dashboard",
            onClick = { navController.navigate("dashboard") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.dashboard_title)
                )
            },
            label = { Text(stringResource(R.string.dashboard_title)) }
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "overview",
            onClick = { navController.navigate("overview") },
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.overview_title)
                )
            },
            label = { Text(stringResource(R.string.overview_title)) }
        )
    }
}