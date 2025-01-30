package com.janpschwietzer.calpal.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.presentation.views.add_product.AddProductScreen
import com.janpschwietzer.calpal.presentation.views.dashboard.DashboardScreen
import com.janpschwietzer.calpal.presentation.views.details.DetailsScreen
import com.janpschwietzer.calpal.presentation.views.overview.OverviewScreen
import com.janpschwietzer.calpal.presentation.views.settings.SettingsScreen
import com.janpschwietzer.calpal.presentation.views.settings.user.UserSettingsScreen

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
        composable(Screen.AddProduct.route) { backStackEntry ->
            val mealType = backStackEntry.arguments?.getString("mealType")
            AddProductScreen(navController, mealType)
        }
        composable(Screen.Details.route) { backStackEntry ->
            val mealType = backStackEntry.arguments?.getString("mealType")
            DetailsScreen(navController, mealType)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(Screen.UserSettings.route) {
            UserSettingsScreen(navController)
        }
    }
}