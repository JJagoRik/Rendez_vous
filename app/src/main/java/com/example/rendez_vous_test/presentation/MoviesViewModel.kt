package com.example.rendez_vous_test.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rendez_vous_test.domain.model.GenreIdName
import com.example.rendez_vous_test.domain.model.Movie
import com.example.rendez_vous_test.domain.use_case.GetGenresUseCase
import com.example.rendez_vous_test.domain.use_case.GetMoviesUseCase
import com.example.rendez_vous_test.domain.use_case.GetPagesUseCase
import com.example.rendez_vous_test.domain.use_case.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FilmsLoadingState {
    object Success : FilmsLoadingState
    object Loading : FilmsLoadingState
    object NoMorePages : FilmsLoadingState
    object Error : FilmsLoadingState
    object LoadingOnStart : FilmsLoadingState
    object Reloading : FilmsLoadingState
    object Refreshing : FilmsLoadingState
}

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getPagesUseCase: GetPagesUseCase,
    application: Application
) : ViewModel() {

    var filmsLoadingState: FilmsLoadingState by mutableStateOf(FilmsLoadingState.LoadingOnStart)

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>> = _movies

    private val _searchedMovies = MutableLiveData<List<Movie>>(emptyList())
    val searchedMovies: LiveData<List<Movie>> = _searchedMovies

    private val _genres = MutableLiveData<List<GenreIdName>>(emptyList())
    val genres: LiveData<List<GenreIdName>> = _genres

    private val _favorites = MutableLiveData<Set<Movie>>(emptySet())
    val favorites: LiveData<Set<Movie>> = _favorites

    private val sharedPreferences = application.getSharedPreferences(
        "movies_prefs",
        Context.MODE_PRIVATE
    )
    var lastSearchQuery: String
        get() = sharedPreferences.getString("last_search_query", "") ?: ""
        set(value) {
            sharedPreferences.edit().putString("last_search_query", value).apply()
        }

    var currentPage = 1
    private var totalPages: Int = 1

    init {
        if (lastSearchQuery.isNotEmpty()) {
            searchMovies(lastSearchQuery)
        }
        getMovies(currentPage)
    }

    fun getMovies(page: Int) {
        viewModelScope.launch {
            try {
                if (filmsLoadingState is FilmsLoadingState.LoadingOnStart){
                    getPages()
                    getGenres()
                }
                val newMovies = getMoviesUseCase(page)
                _movies.value = _movies.value?.plus(newMovies)
                filmsLoadingState = FilmsLoadingState.Success
            } catch (e: Exception) {
                filmsLoadingState = FilmsLoadingState.Error
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val searchResults = searchMoviesUseCase(query)
                _searchedMovies.value = searchResults
                lastSearchQuery = query
                filmsLoadingState = FilmsLoadingState.Success
            } catch (e: Exception) {
                filmsLoadingState = FilmsLoadingState.Error
            }
        }
    }

    fun reloadMoviesAfterEx(page: Int, query: String){
        getMovies(page)
        searchMovies(query)
    }

    fun getGenres() {
        viewModelScope.launch {
            try {
                _genres.value = getGenresUseCase()
            } catch (e: Exception) {
                filmsLoadingState = FilmsLoadingState.Error
            }
        }
    }

    fun getPages() {
        viewModelScope.launch {
            try {
                totalPages = getPagesUseCase()
            } catch (e: Exception) {
                filmsLoadingState = FilmsLoadingState.Error
            }
        }
    }

    fun loadNextPage() {
        if (currentPage < totalPages) {
            currentPage++
            filmsLoadingState = FilmsLoadingState.Loading
            getMovies(currentPage)
        } else {
            filmsLoadingState = FilmsLoadingState.NoMorePages
        }
    }

    fun reloadMovies(){
        filmsLoadingState = FilmsLoadingState.Reloading
    }

    fun loadStartMovies(){
        filmsLoadingState = FilmsLoadingState.LoadingOnStart
    }

    fun refreshMovies(){
        filmsLoadingState = FilmsLoadingState.Reloading
        _movies.value = emptyList()
        currentPage = 1
        getMovies(currentPage)
    }

    fun getMovieById(id: Int, whichDataToUse: String): Movie? {
        if (whichDataToUse == "Start"){
            return movies.value?.find { it.id == id }
        } else if (whichDataToUse == "Search") {
            return searchedMovies.value?.find { it.id == id }
        } else {
            return favorites.value?.find { it.id == id }
        }
    }

    fun addToFavorite(movie: Movie?) {
        movie?.let {
            _favorites.value = _favorites.value?.toMutableSet()?.apply { add(it) }
            Log.d("Favorites", "Movie added: ${it.title}")
        }
    }

    fun removeFromFavorite(movie: Movie?) {
        movie?.let {
            _favorites.value = _favorites.value?.toMutableSet()?.apply { remove(it) }
            Log.d("Favorites", "Movie removed: ${it.title}")
        }
    }

    fun isFavoriteMovie(movie: Movie?): Boolean{
        return _favorites.value?.contains(movie) == true
    }
}