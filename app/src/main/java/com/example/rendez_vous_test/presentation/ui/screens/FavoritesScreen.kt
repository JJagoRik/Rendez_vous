package com.example.rendez_vous_test.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rendez_vous_test.presentation.MoviesViewModel
import com.example.rendez_vous_test.presentation.ui.Screen
import com.example.rendez_vous_test.presentation.ui.components.MovieCard

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: MoviesViewModel
){
    val favoriteMoviesState = viewModel.favorites
    val favoriteMovies = remember { favoriteMoviesState.value }
    val genresState = viewModel.genres.observeAsState(emptyList())
    val genres = genresState.value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(favoriteMovies?.toList() ?: emptyList()) { index, movie ->
            val genre = genres.find { it.id == movie.genreId }?.name ?: "Undetected"
            MovieCard(
                movie = movie,
                genre = genre
            ) {
                navController.navigate(Screen.DetailsScreen.createRoute(movie.id ?: 0, "Favorites"))
            }
        }
    }
}