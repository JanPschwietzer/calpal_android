package com.janpschwietzer.calpal.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.util.enums.MealTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldView(
    title: String = stringResource(R.string.app_name),
    showCloseButton: Boolean = false,
    showFab: Boolean = true,
    navController: NavHostController,
    mealTime: MealTime? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    if (showCloseButton) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    } else {
                        IconButton(onClick = { navController.navigate(Screen.Settings.route) { launchSingleTop = true } }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(R.string.open_settings)
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            NavigationBarView(navController = navController)
        },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.SearchProduct.route) { launchSingleTop = true }
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_product)
                    )
                }
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@PreviewLightDark
@Composable
private fun ScaffoldViewPreview() {
    ScaffoldView(
        title = "CalPal",
        navController = rememberNavController()
    ) { paddingValues ->
        Text(
            "Content",
            modifier = Modifier.padding(paddingValues)
        )
    }
}