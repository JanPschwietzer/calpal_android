package com.janpschwietzer.calpal.presentation.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.navigation.Screen
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScaffold(
        title = stringResource(R.string.settings_title),
        showFab = false,
        showCloseButton = true,
        navController = navController
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.UserSettings.route) { launchSingleTop = true } },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = MaterialTheme.shapes.extraSmall,

            ) {
                Text(stringResource(R.string.user_settings))
            }

            OutlinedButton(
                onClick = { navController.navigate(Screen.ProductSettings.route) { launchSingleTop = true } },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(stringResource(R.string.product_settings))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun SettingsScreenPreview() {
    CalPalTheme {
        SettingsScreen(navController = rememberNavController())
    }
}