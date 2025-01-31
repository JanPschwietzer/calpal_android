package com.janpschwietzer.calpal.presentation.navigation

import android.util.Log
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
            route = "details/{mealTime}",
            arguments = listOf(navArgument("mealTime") { type = NavType.IntType })
        ) { backStackEntry ->
            val mealTime = backStackEntry.arguments?.getInt("mealTime")?.let { MealTime.fromId(it) }
            AddProductScreen(navController, mealTime = mealTime)
        }
        composable(
            route = "add-product/{mealTime}",
            arguments = listOf(navArgument("mealTime") { type = NavType.IntType })
        ) { backStackEntry ->
            val mealTime = backStackEntry.arguments?.getInt("mealTime")?.let { MealTime.fromId(it) }
            AddProductScreen(navController, mealTime = mealTime)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(Screen.UserSettings.route) {
            UserSettingsScreen(navController)
        }
    }
}