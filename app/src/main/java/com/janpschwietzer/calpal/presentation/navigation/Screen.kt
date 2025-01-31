package com.janpschwietzer.calpal.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard: Screen("dashboard")
    object Overview: Screen("overview")
    object AddProduct: Screen("add-product/{barcode}") {
        fun createRoute(barcode: String) = "add-product/$barcode"
    }
    object Details: Screen("details/{mealTime}") {
        fun createRoute(mealTime: String) = "details/$mealTime"
    }
    object SearchProduct: Screen("search-product")
    object Settings: Screen("settings")
    object UserSettings: Screen("settings/user")
}