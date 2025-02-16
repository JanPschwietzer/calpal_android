package com.janpschwietzer.calpal.presentation.views.settings.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.input.BasicInputField
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun ProductSettingsItemScreen(
    navController: NavHostController = rememberNavController(),
    barcode: Long?
) {
    val viewModel: ProductSettingsItemViewModel = hiltViewModel()
    val product by viewModel.product.collectAsState()

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
            modifier = Modifier.padding(paddingValues).padding(8.dp).fillMaxSize()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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
