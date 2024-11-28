package com.example.rendez_vous_test.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rendez_vous_test.presentation.MoviesViewModel
import com.example.rendez_vous_test.presentation.SearchFilmsLoadingState
import com.example.rendez_vous_test.presentation.ui.Screen
import com.example.rendez_vous_test.presentation.ui.components.MovieCard

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: MoviesViewModel
) {
    val searchQuery = remember { mutableStateOf("") }
    val searchedMoviesState = viewModel.searchedMovies.observeAsState(emptyList())
    val searchedMovies = searchedMoviesState.value
    val genresState = viewModel.genres.observeAsState(emptyList())
    val genres = genresState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when(viewModel.searchFilmsLoadingState){
            is SearchFilmsLoadingState.Error -> {
                ErrorScreen {
                    viewModel.searchMovies(searchQuery.toString())
                }
            }
            else -> {
                TextField(
                    value = searchQuery.value,
                    onValueChange = {
                        searchQuery.value = it
                        viewModel.searchMovies(it)
                    },
                    label = { Text("Введите название фильма") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.searchMovies(searchQuery.value)
                        }
                    )
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(searchedMovies) { index, movie ->
                        val genre = genres.find { it.id == movie.genreId }?.name ?: "Undetected"
                        MovieCard(
                            movie = movie,
                            genre = genre
                        ) {
                            navController.navigate(Screen.DetailsScreen.createRoute(movie.id ?: 0, "Search"))
                        }
                    }
                }
            }
        }
    }
}