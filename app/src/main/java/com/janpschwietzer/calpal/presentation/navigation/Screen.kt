package com.janpschwietzer.calpal.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard: Screen("dashboard")
    object Overview: Screen("overview")
    object AddProduct: Screen("add-product")
    object Details: Screen("details")
    object SearchProduct: Screen("search-product")
    object Settings: Screen("settings")
    object UserSettings: Screen("settings/user")
}