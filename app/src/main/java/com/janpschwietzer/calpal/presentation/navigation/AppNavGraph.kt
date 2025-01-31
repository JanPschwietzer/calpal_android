package com.janpschwietzer.calpal.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.janpschwietzer.calpal.presentation.views.product.add.AddProductScreen
import com.janpschwietzer.calpal.presentation.views.dashboard.DashboardScreen
import com.janpschwietzer.calpal.presentation.views.details.DetailsScreen
import com.janpschwietzer.calpal.presentation.views.overview.OverviewScreen
import com.janpschwietzer.calpal.presentation.views.product.search.SearchProductScreen
import com.janpschwietzer.calpal.presentation.views.settings.SettingsScreen
import com.janpschwietzer.calpal.presentation.views.settings.user.UserSettingsScreen
import com.janpschwietzer.calpal.util.enums.MealTime

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }
        composable(Screen.Overview.route) {
            OverviewScreen(navController)
        }


        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("mealTime") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }),
        ) { backStackEntry ->
            val mealTime = backStackEntry.arguments?.getString("mealTime")?.let { MealTime.fromId(it.toIntOrNull()) }
            DetailsScreen(navController, mealTime = mealTime)
        }
        composable(
            route = Screen.AddProduct.route,
            arguments = listOf(navArgument("mealTime") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }),
        ) { backStackEntry ->
            val mealTime = backStackEntry.arguments?.getString("mealTime")?.let { MealTime.fromId(it.toIntOrNull()) }
            AddProductScreen(navController, mealTime = mealTime)
        }
        composable(
            route = Screen.SearchProduct.route,
            arguments = listOf(navArgument("mealTime") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }),
        ) { backStackEntry ->
            val mealTime = backStackEntry.arguments?.getString("mealTime")?.let { MealTime.fromId(it.toIntOrNull()) }
            SearchProductScreen(navController, mealTime = mealTime)
        }


        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(Screen.UserSettings.route) {
            UserSettingsScreen(navController)
        }
    }
}