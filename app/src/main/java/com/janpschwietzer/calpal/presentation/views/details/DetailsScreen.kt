package com.janpschwietzer.calpal.presentation.views.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.ScaffoldView
import com.janpschwietzer.calpal.util.enums.MealTime

/*
    Dieser Screen soll eine Liste von hinzugefügten Produkten zu einer Mahlzeit anzeigen.
 */
@Composable
fun DetailsScreen(
    navController: NavHostController,
    mealTime: MealTime?
) {
    if (mealTime == null) {
        navController.popBackStack()
    }

    ScaffoldView(
        title = stringResource(R.string.details_title),
        showCloseButton = true,
        navController = navController,
        mealTime = mealTime
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            mealTime?.getDisplayedString(navController.context)?.let { Text(it) }
        }
    }
}

@PreviewLightDark
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(navController = rememberNavController(), mealTime = MealTime.BREAKFAST)
}