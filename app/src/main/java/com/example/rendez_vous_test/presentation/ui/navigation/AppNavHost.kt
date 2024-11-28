package com.example.rendez_vous_test.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rendez_vous_test.presentation.MoviesViewModel
import com.example.rendez_vous_test.presentation.ui.Screen
import com.example.rendez_vous_test.presentation.ui.screens.DetailsScreen
import com.example.rendez_vous_test.presentation.ui.screens.FavoritesScreen
import com.example.rendez_vous_test.presentation.ui.screens.SearchScreen
import com.example.rendez_vous_test.presentation.ui.screens.StartScreen

@Composable
fun AppNavHost(
    viewModel: MoviesViewModel,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.route
    ){
        composable(route = Screen.StartScreen.route){
            StartScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.DetailsScreen.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("whichDataToUse") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt("movieId") ?: 0
            val whichDataToUse = navBackStackEntry.arguments?.getString("whichDataToUse") ?: ""
            DetailsScreen(
                movieId = movieId,
                whichDataToUse = whichDataToUse,
                viewModel = viewModel,
            )
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.FavoritesScreen.route) {
            FavoritesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}