package com.janpschwietzer.calpal.presentation.views.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.presentation.components.list.EatenProductListItem
import com.janpschwietzer.calpal.presentation.components.list.ProductListItem
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
        title = stringResource(R.string.product_settings),
        showFab = false,
        showBottomBar = false,
        showCloseButton = true
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        )
        {
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(eatenProducts) { product ->
                    EatenProductListItem(product, products.find { it.barcode == product.barcode }) {
                    }
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