package com.example.rendez_vous_test.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rendez_vous_test.presentation.FilmsLoadingState
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

    when(viewModel.filmsLoadingState){
        is FilmsLoadingState.Error -> {
            ErrorScreen {
                if (viewModel.movies.value.isNullOrEmpty() == true){
                    viewModel.loadStartMovies()
                } else {
                    viewModel.reloadMovies()
                }
                viewModel.reloadMoviesAfterEx(viewModel.currentPage, viewModel.lastSearchQuery)
            }
        }

        is FilmsLoadingState.Reloading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            if (favoriteMovies?.isEmpty() ?: true) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Вы ничего не добавили в избранное",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            } else {
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
        }
    }
}