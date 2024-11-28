package com.example.rendez_vous_test.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rendez_vous_test.domain.model.GenreIdName
import com.example.rendez_vous_test.domain.model.Movie
import com.example.rendez_vous_test.presentation.FilmsLoadingState
import com.example.rendez_vous_test.presentation.MoviesViewModel
import com.example.rendez_vous_test.presentation.ui.Screen

@Composable
fun MoviesGrid(
    movies: List<Movie>,
    genres: List<GenreIdName>,
    viewModel: MoviesViewModel,
    navController: NavController
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(movies) { index, movie ->
            val genre = genres.find { it.id == movie.genreId }?.name ?: "Undetected"
            MovieCard(
                movie = movie,
                genre = genre
            ) {
                navController.navigate(Screen.DetailsScreen.createRoute(movie.id ?: 0, "Start"))
            }

            if (index == movies.size - 1 && lazyGridState.layoutInfo.visibleItemsInfo.isNotEmpty()) {
                val lastVisibleItemIndex =
                    lazyGridState.layoutInfo.visibleItemsInfo.last().index
                if (lastVisibleItemIndex == index) {
                    viewModel.loadNextPage()
                }
            }
        }

        if (viewModel.filmsLoadingState is FilmsLoadingState.Loading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}