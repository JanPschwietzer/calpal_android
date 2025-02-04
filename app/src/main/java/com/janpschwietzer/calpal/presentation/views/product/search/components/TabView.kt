package com.janpschwietzer.calpal.presentation.views.product.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.util.enums.GreenScore
import com.janpschwietzer.calpal.util.enums.NutriScore

@Composable
fun TabView(
    navController: NavHostController,
    selectedTabIndex: Int,
    tabs: List<Int>,
    favoriteProducts: List<ProductModel>,
    mostAddedProducts: List<ProductModel>,
    onTabSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .fillMaxWidth()
                )
            }
        ) {
            tabs.forEachIndexed { index, resId ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(text = stringResource(resId)) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> ProductTab(favoriteProducts, navController)
            1 -> ProductTab(mostAddedProducts, navController)
            else -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No content")
            }
        }
    }
}



@PreviewLightDark
@Composable
private fun TabViewPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TabView(
            navController = rememberNavController(),
            selectedTabIndex = 0,
            tabs = listOf(R.string.favorites, R.string.often_added),
            favoriteProducts = listOf(
                ProductModel(
                    barcode = 123456789,
                    name = "Test Product",
                    brand = "Test Brand",
                    kcal = 100,
                    carbs = 10.0,
                    sugar = 5.0,
                    fat = 5.0,
                    saturatedFat = 2.0,
                    protein = 10.0,
                    salt = 1.0,
                    fiber = 2.0,
                    portionSize = 100,
                    portionUnit = "g",
                    nutriScore = NutriScore.A,
                    greenScore = GreenScore.A,
                    timesAdded = 0,
                    isFavorite = false
                ),
                ProductModel(
                    barcode = 987654321,
                    name = "Test Product 2",
                    brand = "Test Brand 2",
                    kcal = 200,
                    carbs = 20.0,
                    sugar = 10.0,
                    fat = 10.0,
                    saturatedFat = 4.0,
                    protein = 20.0,
                    salt = 2.0,
                    fiber = 4.0,
                    portionSize = 200,
                    portionUnit = "g",
                    nutriScore = NutriScore.B,
                    greenScore = GreenScore.B,
                    timesAdded = 0,
                    isFavorite = false
                )
            ),
            mostAddedProducts = listOf(
                ProductModel(
                    barcode = 123456789,
                    name = "Test Product",
                    brand = "Test Brand",
                    kcal = 100,
                    carbs = 10.0,
                    sugar = 5.0,
                    fat = 5.0,
                    saturatedFat = 2.0,
                    protein = 10.0,
                    salt = 1.0,
                    fiber = 2.0,
                    portionSize = 100,
                    portionUnit = "g",
                    nutriScore = NutriScore.A,
                    greenScore = GreenScore.A,
                    timesAdded = 0,
                    isFavorite = false
                ),
                ProductModel(
                    barcode = 987654321,
                    name = "Test Product 2",
                    brand = "Test Brand 2",
                    kcal = 200,
                    carbs = 20.0,
                    sugar = 10.0,
                    fat = 10.0,
                    saturatedFat = 4.0,
                    protein = 20.0,
                    salt = 2.0,
                    fiber = 4.0,
                    portionSize = 200,
                    portionUnit = "g",
                    nutriScore = NutriScore.B,
                    greenScore = GreenScore.B,
                    timesAdded = 0,
                    isFavorite = false
                )
            ),
            onTabSelected = {}
        )
    }
}