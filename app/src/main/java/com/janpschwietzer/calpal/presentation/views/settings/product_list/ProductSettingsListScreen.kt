package com.janpschwietzer.calpal.presentation.views.settings.product_list


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.list.ProductListItem
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.navigation.Screen

@Composable
fun ProductSettingsListScreen(
    navController: NavHostController = rememberNavController()
) {

    val viewModel: ProductSettingsListViewModel = hiltViewModel()
    val products by viewModel.products.collectAsState()
    val filterString by viewModel.filterString.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    CustomScaffold(
        navController = navController,
        title = stringResource(R.string.product_settings),
        showFab = false,
        showBottomBar = false,
        showCloseButton = true
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        )
        {
            OutlinedTextField(
                value = filterString,
                onValueChange = {
                    viewModel.filterProducts(it)
                },
                label = { Text(stringResource(R.string.filter)) },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(products) { product ->
                    ProductListItem(
                        product = product,
                        onClick = {
                            navController.navigate(Screen.ProductSettingsItem.createRoute(product.barcode.toString()))
                        }
                    )
                }
            }
        }
    }
}