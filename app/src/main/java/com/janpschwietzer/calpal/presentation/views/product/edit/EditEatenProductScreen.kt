package com.janpschwietzer.calpal.presentation.views.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.SaveButton
import com.janpschwietzer.calpal.presentation.components.dropdown.MealTimeDropdown
import com.janpschwietzer.calpal.presentation.components.dropdown.PortionUnitDropdown
import com.janpschwietzer.calpal.presentation.components.input.BasicInputField
import com.janpschwietzer.calpal.presentation.views.product.edit.components.ProductInfoTable
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.util.enums.MealTime
import com.janpschwietzer.calpal.util.enums.PortionUnit

@Composable
fun EditEatenProductScreen(
    navController: NavHostController,
    id: Int?
) {
    val viewModel: EditEatenProductViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadProduct(id)
    }
    val eatenProduct by viewModel.eatenProduct.collectAsState()
    val product by viewModel.product.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val scrollState = rememberScrollState()

    CustomScaffold(
        title = "${product?.name}",
        showCloseButton = true,
        showBottomBar = false,
        showFab = false,
        navController = navController,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (id == null) {
                Text(
                    text = stringResource(R.string.no_product_found),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Text(
                            text = product?.brand ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(
                            text = product?.name ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { navController.navigate(Screen.ProductSettings.route + "/${product?.barcode}") }
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
                            contentDescription = stringResource(R.string.favorite_product)
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
                        value = amount,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(8.dp),
                        onValueChange = viewModel::setEatenProductAmount,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        )
                    )

                    if (product?.portionSize != null) {
                        PortionUnitDropdown(
                            context = navController.context,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(8.dp),
                            selected = eatenProduct?.unit ?: PortionUnit.METRICAL,
                            onSelected = viewModel::setEatenProductPortionUnit
                        )
                    }
                }

                MealTimeDropdown(
                    context = navController.context,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    selected = eatenProduct?.meal ?: MealTime.BREAKFAST,
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.product_information),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        product?.let { ProductInfoTable(it) }
                    }
                }
            }
        }
    }
}