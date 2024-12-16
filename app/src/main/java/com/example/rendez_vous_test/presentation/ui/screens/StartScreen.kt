package com.example.rendez_vous_test.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.rendez_vous_test.presentation.FilmsLoadingState
import com.example.rendez_vous_test.presentation.MoviesViewModel
import com.example.rendez_vous_test.presentation.ui.components.MoviesGrid
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: MoviesViewModel
){
    val moviesState = viewModel.movies.observeAsState(emptyList())
    val movies = moviesState.value
    val genresState = viewModel.genres.observeAsState(emptyList())
    val genres = genresState.value
    val isRefreshing = viewModel.filmsLoadingState is FilmsLoadingState.Refreshing

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                viewModel.refreshMovies()
            }
        ) {
            Column {
                when (viewModel.filmsLoadingState) {
                    is FilmsLoadingState.LoadingOnStart -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
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

                    is FilmsLoadingState.Error -> {
                        ErrorScreen {
                            if (movies.isEmpty()){
                                viewModel.loadStartMovies()
                            } else {
                                viewModel.reloadMovies()
                            }
                            viewModel.getMovies(viewModel.currentPage)
                        }
                    }

                    else -> {
                        MoviesGrid(movies, genres, viewModel, navController)
                    }
                }
            }
        }
    }
}