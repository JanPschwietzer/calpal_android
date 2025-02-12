package com.janpschwietzer.calpal.presentation.views.product.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.presentation.components.list.ProductListItem
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.util.enums.GreenScore
import com.janpschwietzer.calpal.util.enums.NutriScore

@Composable
fun ProductTab(
    products: List<ProductModel>,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        products.forEach { product ->
            ProductItem(product, navController)
        }
    }
}

@Composable
private fun ProductItem(
    product: ProductModel,
    navController: NavHostController
) {
    ProductListItem(
        product = product,
        onClick = {
            navController.navigate(Screen.AddProduct.createRoute(product.barcode.toString()))
        }
    )
}

@PreviewLightDark
@Composable
private fun ProductTabPreview() {
    ProductTab(
        navController = rememberNavController(),
        products = listOf(
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
        )
    )
}