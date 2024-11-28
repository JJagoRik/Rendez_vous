package com.example.rendez_vous_test.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import com.example.rendez_vous_test.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rendez_vous_test.presentation.ui.Screen

@Composable
fun BottomMenu(navController: NavController) {
    var activeIndex = remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .requiredHeight(80.dp)
            .shadow(elevation = 0.5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        BottomMenuCell(
            image = R.drawable.home,
            description = "Главная",
            isActive = activeIndex.value == 0
        ) {
            activeIndex.value = 0
            navController.navigate(Screen.StartScreen.route)
        }

        BottomMenuCell(
            image = R.drawable.favorites,
            description = "Избранное",
            isActive = activeIndex.value == 1
        ) {
            activeIndex.value = 1
            navController.navigate(Screen.FavoritesScreen.route)
        }

        BottomMenuCell(
            image = R.drawable.profile,
            description = "Профиль",
            isActive = activeIndex.value == 2
        ) {
            activeIndex.value = 2
        }

        BottomMenuCell(
            image = R.drawable.search,
            description = "Поиск",
            isActive = activeIndex.value == 3
        ) {
            activeIndex.value = 3
            navController.navigate(Screen.SearchScreen.route)
        }
    }
}