package com.janpschwietzer.calpal.presentation.views.product.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.views.barcodescanner.BarcodeScannerScreen
import com.janpschwietzer.calpal.presentation.views.product.search.components.ProductTab
import com.janpschwietzer.calpal.presentation.views.product.search.components.TabView
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun SearchProductScreen(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: SearchEatenProductViewModel = hiltViewModel()
    val textFieldValue by viewModel.textFieldValue.collectAsState()

    val favoriteProducts by viewModel.favoriteProducts.collectAsState()
    val mostAddedProducts by viewModel.mostAddedProducts.collectAsState()

    var showScanner by remember { mutableStateOf(false) }
    val selectedTabIndex by viewModel.selectedTabIndex

    LaunchedEffect(Unit) {
        viewModel.loadFavoriteProducts()
        viewModel.loadMostAddedProducts()
    }

    if (showScanner) {
        BarcodeScannerScreen(
            navController = navController,
            onBarcodeScanned = { barcode ->
                viewModel.setTextFieldValue(barcode)
                viewModel.navigateToAddProductView(navController)
            },
            onCloseClicked = { showScanner = false }
        )
        return
    }

    CustomScaffold(
        title = stringResource(R.string.add_product_title),
        showCloseButton = true,
        showFab = false,
        showBottomBar = false,
        navController = navController
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                label = { Text("search") },
                value = textFieldValue,
                onValueChange = { viewModel.setTextFieldValue(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.navigateToAddProductView(navController)
                        }
                    ),
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    if (textFieldValue.isEmpty()) {
                        IconButton(
                            onClick = {
                                showScanner = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = "Scan barcode"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                viewModel.navigateToAddProductView(navController)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search"
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            TabView(
                navController = navController,
                selectedTabIndex = selectedTabIndex,
                tabs = viewModel.tabs,
                favoriteProducts = favoriteProducts,
                mostAddedProducts = mostAddedProducts,
                onTabSelected = viewModel::onTabSelected
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SearchProductScreenPreview() {
    CalPalTheme {
        SearchProductScreen(navController = rememberNavController())
    }
}