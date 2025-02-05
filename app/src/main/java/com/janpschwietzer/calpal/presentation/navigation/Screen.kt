package com.janpschwietzer.calpal.presentation.navigation

sealed class Screen(val route: String) {
    data object Dashboard: Screen("dashboard")
    data object Overview: Screen("overview")
    data object AddProduct: Screen("add-product/{barcode}") {
        fun createRoute(barcode: String) = "add-product/$barcode"
    }
    data object Details: Screen("details/{mealTime}") {
        fun createRoute(mealTime: String) = "details/$mealTime"
    }
    data object SearchProduct: Screen("search-product")
    data object Settings: Screen("settings")
    data object UserSettings: Screen("settings/user")
    data object ProductSettings: Screen("settings/product")
    data object ProductSettingsItem: Screen("settings/product/{barcode}") {
        fun createRoute(barcode: String) = "add-product/$barcode" //TODO: change
    }
}