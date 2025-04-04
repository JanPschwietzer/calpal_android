package com.janpschwietzer.calpal.presentation.views.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.list.EatenProductListItem
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.util.enums.MealTime

@Composable
fun DetailsScreen(
    navController: NavHostController,
    mealTime: MealTime?
) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val eatenProducts by viewModel.eatenProducts.collectAsState()
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        if (mealTime == null) {
            navController.popBackStack()
        }
        viewModel.getProducts(mealTime!!)
    }

    CustomScaffold(
        navController = navController,
        title = mealTime?.getDisplayedString(context = navController.context) ?: "Details",
        showFab = false,
        showBottomBar = false,
        showCloseButton = true
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        )
        {
            LazyColumn {
                items(eatenProducts) { product ->
                    EatenProductListItem(
                        product,
                        products.find { it.barcode == product.barcode },
                        onClick = {
                            navController.navigate(Screen.EditEatenProduct.createRoute(product.id.toString()))
                        },
                        onDelete = { viewModel.RemoveEatenItem(product) }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DetailsScreenPreview() {
    DetailsScreen(navController = rememberNavController(), mealTime = MealTime.BREAKFAST)
}