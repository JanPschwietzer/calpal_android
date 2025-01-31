package com.janpschwietzer.calpal.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard: Screen("dashboard")
    object Overview: Screen("overview")
    object AddProduct: Screen("add-product/{mealTime}")
    object Details: Screen("details/{mealTime}")
    object Settings: Screen("settings")
    object UserSettings: Screen("settings/user")
}