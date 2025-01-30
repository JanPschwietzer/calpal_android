package com.janpschwietzer.calpal.presentation.views.product.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavHostController,
    mealType: String? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_product_title)) },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(URLDecoder.decode(
                mealType ?: "",
                "UTF-8"
            ))
        }
    }
}

@PreviewLightDark
@Composable
private fun AddProductScreenPreview() {
    CalPalTheme {
        AddProductScreen(navController = rememberNavController())
    }
}