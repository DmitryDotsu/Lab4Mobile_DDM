package com.example.lab4mobile_ddm.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object Category : Destinations("category/{categoryId}") {
        fun passCategoryId(categoryId: String): String = "category/$categoryId"
    }
    object Detail : Destinations("detail/{recommendationId}") {
    }
    object About : Destinations("about")
    object Settings : Destinations("settings")
}

data class NavigationState(
    val selectedBottomNavItem: Int = 0,
    val isDrawerOpen: Boolean = false
)