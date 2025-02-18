package com.example.rendez_vous_test.data.repository

import com.example.rendez_vous_test.data.datasource.remote.TmdbApi
import com.example.rendez_vous_test.data.mapper.GenresMapper
import com.example.rendez_vous_test.data.mapper.PagesMapper
import com.example.rendez_vous_test.data.mapper.ResultsMapper
import com.example.rendez_vous_test.domain.model.GenreIdName
import com.example.rendez_vous_test.domain.model.Movie
import com.example.rendez_vous_test.domain.repository.TmdbRepository

const val API_KEY = "a515e45f78151520c7feef972ad69f5f"

class TmdbRepositoryImpl(
    private val tmdbApi: TmdbApi,
    private val resultsMapper: ResultsMapper,
    private val genresMapper: GenresMapper,
    private val pagesMapper: PagesMapper
) : TmdbRepository {

    override suspend fun getMovies(page: Int) : List<Movie> {
        return tmdbApi.getMovies(API_KEY, page).results.map { resultsMapper.map(it) }
    }

    override suspend fun getPages(): Int {
        return pagesMapper.map(tmdbApi.getPages(API_KEY))
    }

    override suspend fun getGenres(): List<GenreIdName> {
        return tmdbApi.getGenres(API_KEY).genres.map { genresMapper.map(it) }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return tmdbApi.searchMovies(API_KEY, query).results.map { resultsMapper.map(it) }
    }
}