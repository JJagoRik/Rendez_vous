package com.example.rendez_vous_test.presentation.ui

sealed class Screen(val route: String){
    object StartScreen : Screen("start_screen")
    object DetailsScreen : Screen("details_screen/{movieId}/{whichDataToUse}") {
        fun createRoute(movieId: Int, whichDataToUse: String): String =
            "details_screen/${movieId}/${whichDataToUse}"
    }
    object SearchScreen : Screen("search_screen")
    object FavoritesScreen : Screen("favorites_screen")
}