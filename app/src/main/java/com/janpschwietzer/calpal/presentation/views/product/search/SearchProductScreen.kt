package com.janpschwietzer.calpal.presentation.views.product.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.janpschwietzer.calpal.presentation.components.DropdownView
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import com.janpschwietzer.calpal.util.enums.Gender
import com.janpschwietzer.calpal.util.enums.MealTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductScreen(
    navController: NavHostController = rememberNavController(),
    mealTime: MealTime? = null
) {
    var textFieldValue by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Search Product") },
                actions = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                }
            )
        }

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
                            //TODO: implement search for text via ViewModel
                            Log.d("ProductSearchScreen", "search for text")
                        }
                    ),
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    if (textFieldValue.isEmpty()) {
                        IconButton(
                            onClick = {
                                //TODO: implement camera scan for barcode screen via ViewModel
                                Log.d("ProductSearchScreen", "scan barcode")
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
                                //TODO: implement search for text via ViewModel
                                Log.d("ProductSearchScreen", "search for text")
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