package com.janpschwietzer.calpal.presentation.views.settings.product_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.list.ProductListItem
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.navigation.Screen

@Composable
fun ProductSettingsListScreen(
    navController: NavHostController = rememberNavController()
) {

    val viewModel: ProductSettingsListViewModel = hiltViewModel()
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    CustomScaffold(
        navController = navController,
        title = stringResource(R.string.product_settings),
        showFab = false,
        showBottomBar = false,
        showCloseButton = true
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(8.dp)
        ) {
            products.forEach { product ->
                ProductListItem(
                    product = product,
                    navController = navController,
                    onClick = {
                        navController.navigate(Screen.ProductSettingsItem.createRoute(product.barcode.toString()))
                    }
                )
            }
        }
    }
}