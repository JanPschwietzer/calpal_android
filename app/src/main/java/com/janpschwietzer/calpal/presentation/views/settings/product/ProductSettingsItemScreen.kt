package com.janpschwietzer.calpal.presentation.views.settings.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.SaveButton
import com.janpschwietzer.calpal.presentation.components.dropdown.PortionUnitDropdown
import com.janpschwietzer.calpal.presentation.components.input.BasicInputField
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import com.janpschwietzer.calpal.util.enums.PortionUnit

@Composable
fun ProductSettingsItemScreen(
    navController: NavHostController = rememberNavController(),
    barcode: Long?
) {
    val viewModel: ProductSettingsItemViewModel = hiltViewModel()
    val product by viewModel.product.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.loadProduct(barcode)
    }

    CustomScaffold(
        navController = navController,
        title = stringResource(R.string.product_settings),
        showFab = false,
        showBottomBar = false,
        showCloseButton = true
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
                maxLines = 1,
                textAlign = TextAlign.Center,
                text = "Barcode: ${product.barcode}"
            )

            BasicInputField(
                keyboardOptions = KeyboardOptions.Default,
                label = stringResource(R.string.product_name),
                value = product.name,
                onValueChange = viewModel::updateName
            )
            BasicInputField(
                keyboardOptions = KeyboardOptions.Default,
                label = stringResource(R.string.product_brand),
                value = product.brand,
                onValueChange = viewModel::updateBrand
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.kcal),
                    value = product.kcal?.toString(),
                    onValueChange = viewModel::updateKcal
                )

                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.protein),
                    value = product.protein?.toString(),
                    onValueChange = viewModel::updateProtein
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.portion_size),
                    value = product.portionSize?.toString(),
                    onValueChange = viewModel::updatePortionSize
                )

                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.portion_unit),
                    value = product.portionUnit,
                    onValueChange = viewModel::updatePortionUnit
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.carbs),
                    value = product.carbs?.toString(),
                    onValueChange = viewModel::updateCarbs
                )

                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.sugar),
                    value = product.sugar?.toString(),
                    onValueChange = viewModel::updateSugar
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.fat),
                    value = product.fat?.toString(),
                    onValueChange = viewModel::updateFat
                )

                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.saturated),
                    value = product.saturatedFat?.toString(),
                    onValueChange = viewModel::updateSaturatedFat
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.salt),
                    value = product.salt?.toString(),
                    onValueChange = viewModel::updateSalt
                )

                BasicInputField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = stringResource(R.string.fiber),
                    value = product.fiber?.toString(),
                    onValueChange = viewModel::updateFiber
                )
            }

            //TODO: Vielleicht noch Nutriscore und Ecoscore bearbeitbar machen

            SaveButton(modifier = Modifier.fillMaxWidth()) {
                viewModel.saveProduct()
                navController.popBackStack()
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ProductSettingsItemViewPreview() {
    CalPalTheme {
        ProductSettingsItemScreen(barcode = 123456789)
    }
}
