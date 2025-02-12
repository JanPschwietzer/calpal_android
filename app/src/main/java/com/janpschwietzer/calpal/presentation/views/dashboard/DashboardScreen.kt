package com.janpschwietzer.calpal.presentation.views.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ChartModel
import com.janpschwietzer.calpal.presentation.views.dashboard.components.MealListItem
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.presentation.views.dashboard.components.PieChart
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import com.janpschwietzer.calpal.util.enums.MealTime

@Composable
fun DashboardScreen(navController: NavHostController) {

    val scrollState = rememberScrollState()
    val viewModel: DashboardViewModel = hiltViewModel()

    val maxCalories by viewModel.maxCalories.collectAsState()
    val eatenCalories by viewModel.eatenCalories.collectAsState()
    val eatenMeals by viewModel.totalEatenFlow.collectAsState()

    LaunchedEffect(Unit) {
         viewModel.loadData()
    }

    CustomScaffold(
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
            PieChart(
                modifier = Modifier.padding(40.dp),
                maxCalories = maxCalories?: 2000,
                charts = listOf(
                    ChartModel(value = eatenCalories, color = MaterialTheme.colorScheme.primary, description = stringResource(R.string.eaten))
                ),
                size = 300.dp
            )

            Column {
                for (value in MealTime.entries.map { it }) {
                    MealListItem(
                        navController = navController,
                        meal = value,
                        caloriesEaten = eatenMeals[value] ?: 0,
                        caloriesGoal = ((maxCalories?.times(value.factor))?: (2000*value.factor)).toInt()
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
