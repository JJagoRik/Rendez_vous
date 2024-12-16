package com.example.rendez_vous_test.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import com.example.rendez_vous_test.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rendez_vous_test.presentation.ui.Screen
import androidx.compose.runtime.getValue

@Composable
fun BottomMenu(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .requiredHeight(80.dp)
            .shadow(elevation = 0.5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        BottomMenuCell(
            image = R.drawable.home,
            description = "Главная",
            isActive = currentRoute == Screen.StartScreen.route
        ) {
            navController.navigate(Screen.StartScreen.route) {
                popUpTo(Screen.StartScreen.route) { inclusive = true }
            }
        }

        BottomMenuCell(
            image = R.drawable.favorites,
            description = "Избранное",
            isActive = currentRoute == Screen.FavoritesScreen.route
        ) {
            navController.navigate(Screen.FavoritesScreen.route) {
                popUpTo(Screen.FavoritesScreen.route) { inclusive = true }
            }
        }

        BottomMenuCell(
            image = R.drawable.search,
            description = "Поиск",
            isActive = currentRoute == Screen.SearchScreen.route
        ) {
            navController.navigate(Screen.SearchScreen.route) {
                popUpTo(Screen.SearchScreen.route) { inclusive = true }
            }
        }
    }
}