package com.example.rendez_vous_test.data.datasource.remote

import com.example.rendez_vous_test.data.model.GenresList
import com.example.rendez_vous_test.data.model.PopularMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi{
    @GET("movie/popular")
    suspend fun getPages(
        @Query("api_key") apikey: String,
    ) : PopularMovies

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ) : PopularMovies

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apikey: String,
    ) : GenresList

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apikey: String,
        @Query("query") query: String
    ): PopularMovies
}