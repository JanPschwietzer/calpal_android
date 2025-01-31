package com.janpschwietzer.calpal.presentation.views.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ChartModel
import com.janpschwietzer.calpal.presentation.components.MealListItemView
import com.janpschwietzer.calpal.presentation.components.ScaffoldView
import com.janpschwietzer.calpal.presentation.components.PieChartView
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import com.janpschwietzer.calpal.util.enums.MealTime

@Composable
fun DashboardScreen(navController: NavHostController) {

    val scrollState = rememberScrollState()

    ScaffoldView(
        navController = navController
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            PieChartView(
                modifier = Modifier
                    .padding(32.dp),
                charts = listOf(
                    ChartModel(percentage = 10, value = 200, color = MaterialTheme.colorScheme.primary, description = stringResource(R.string.eaten)),
                    ChartModel(percentage = 90, value = 1800, color = MaterialTheme.colorScheme.primaryContainer, description = stringResource(R.string.remaining))
                ),
                size = 300.dp
            )

            Column {
                for (value in MealTime.entries.map { it }) {
                    MealListItemView(
                        navController = navController,
                        meal = value,
                        caloriesEaten = 0,
                        caloriesGoal = 600
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DashboardScreenPreview() {
    CalPalTheme {
        DashboardScreen(navController = rememberNavController())
    }
}
