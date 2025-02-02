package com.janpschwietzer.calpal.presentation.views.product.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.presentation.views.barcodescanner.BarcodeScannerScreen
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import java.net.URLEncoder

@Composable
fun SearchProductScreen(
    navController: NavHostController = rememberNavController()
) {
    var showScanner by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }

    if (showScanner) {
        BarcodeScannerScreen(
            navController = navController,
            onBarcodeScanned = { barcode ->
                navController.navigate(Screen.AddProduct.createRoute(URLEncoder.encode(barcode, "UTF-8"))) { launchSingleTop = true }
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
                onValueChange = { textFieldValue = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                        onSearch = {
                            //TODO: In viewModel auslagern
                            navController.navigate(Screen.AddProduct.createRoute(textFieldValue)){ launchSingleTop = true }
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
                                navController.navigate(Screen.AddProduct.createRoute(textFieldValue)){ launchSingleTop = true }
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
            //TODO: Add 2 Tabs for favorited products and often added products get Data from ViewModel
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