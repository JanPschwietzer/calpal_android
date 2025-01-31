package com.janpschwietzer.calpal.presentation.layout

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
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.ui.theme.calpalBlue
import com.janpschwietzer.calpal.ui.theme.onCalpalBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    title: String = stringResource(R.string.app_name),
    showCloseButton: Boolean = false,
    showFab: Boolean = true,
    showBottomBar: Boolean = true,
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = {
                    if (showCloseButton) {
                        IconButton(
                            colors = IconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.Gray
                            ),
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    } else {
                        IconButton(
                            colors = IconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.Gray
                            ),
                            onClick = { navController.navigate(Screen.Settings.route) { launchSingleTop = true } }
                        ) {
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
            if (showBottomBar) {
                CustomNavigationBar(navController = navController)
            }
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
    CustomScaffold(
        title = "CalPal",
        navController = rememberNavController()
    ) { paddingValues ->
        Text(
            "Content",
            modifier = Modifier.padding(paddingValues)
        )
    }
}