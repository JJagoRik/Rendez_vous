package com.example.rendez_vous_test.domain.repository

import com.example.rendez_vous_test.domain.model.GenreIdName
import com.example.rendez_vous_test.domain.model.Movie

interface TmdbRepository {
    suspend fun getMovies(page: Int) : List<Movie>

    suspend fun getPages(): Int

    suspend fun getGenres() : List<GenreIdName>

    suspend fun searchMovies(query: String) : List<Movie>
}