package com.janpschwietzer.calpal.presentation.views.product.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.data.model.ProductModel
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.AddProduct.createRoute(product.barcode.toString()))
            }
            .padding(8.dp)
    ) {
        //TODO: Add image of product
        Column {
            Text(
                text = product.name ?: "",
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = product.brand ?: "",
                maxLines = 1
            )
        }
    }
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