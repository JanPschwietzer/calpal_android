package com.janpschwietzer.calpal.presentation.views.product.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun AddProductScreen(
    navController: NavHostController,
    barcode: String?
) {

    val viewModel: UserSettingsViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadProduct(barcode)
    }

    CustomScaffold(
        title = stringResource(R.string.add_product_title),
        showCloseButton = true,
        showBottomBar = false,
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

                    ) {
                    Text(viewModel.product.value?.name ?: "")
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AddProductScreenPreview() {
    CalPalTheme {
        AddProductScreen(
            navController = rememberNavController(),
            barcode = "123456789"
        )
    }
}