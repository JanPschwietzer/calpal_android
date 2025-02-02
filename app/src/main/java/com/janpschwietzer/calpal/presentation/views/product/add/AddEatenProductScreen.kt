package com.janpschwietzer.calpal.presentation.views.product.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.SaveButton
import com.janpschwietzer.calpal.presentation.components.dropdown.MealTimeDropdown
import com.janpschwietzer.calpal.presentation.components.dropdown.PortionUnitDropdown
import com.janpschwietzer.calpal.presentation.components.input.BasicInputField
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun AddEatenProductScreen(
    navController: NavHostController,
    barcode: String?
) {

    val viewModel: AddEatenProductViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadProduct(barcode)
    }
    val eatenProduct by viewModel.eatenProduct.collectAsState()
    val product by viewModel.product.collectAsState()
    val amount by viewModel.amount.collectAsState()


    CustomScaffold(
        title = stringResource(R.string.add_product_title),
        showCloseButton = true,
        showBottomBar = false,
        showFab = false,
        navController = navController,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (barcode == null) {
                //TODO: Show screen to add product manually
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            viewModel.product.value?.name ?: "",
                            maxLines = 1,
                            modifier = Modifier.padding(16.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            enabled = false,
                            onClick = { navController.navigate(Screen.Settings.route) { launchSingleTop = true } } //TODO: Das spezifische Produkt soll hier editierbar sein
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(R.string.edit_product)
                            )
                        }

                        IconButton(
                            onClick = viewModel::favoriteProduct
                        ) {
                            Icon(
                                imageVector = if (product?.isFavorite == true)
                                    Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = stringResource(R.string.edit_product)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        BasicInputField(
                            label = viewModel.buildPortionUnitString(context = navController.context),
                            value = amount?.toString(),
                            modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp),
                            onValueChange = viewModel::setEatenProductAmount,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            )
                        )

                        if (product?.portionSize != null) {
                            PortionUnitDropdown(
                                context = navController.context,
                                modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp),
                                selected = eatenProduct.unit,
                                onSelected = viewModel::setEatenProductPortionUnit
                            )
                        }
                    }

                    MealTimeDropdown(
                        context = navController.context,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        selected = eatenProduct.meal,
                        onSelected = viewModel::setEatenProductMealTime
                    )

                    SaveButton(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        viewModel.saveEatenProduct()
                        navController.navigate(Screen.Dashboard.route) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AddEatenProductScreenPreview() {
    CalPalTheme {
        AddEatenProductScreen(
            navController = rememberNavController(),
            barcode = "5000112552232"
        )
    }
}