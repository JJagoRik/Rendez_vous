package com.example.rendez_vous_test.data.mapper

import com.example.rendez_vous_test.data.model.Results
import com.example.rendez_vous_test.domain.model.Movie

class ResultsMapper {
    fun map(movie: Results): Movie {
        return Movie(
            id = movie.id,
            title = movie.title ?: "Unknown Title",
            movieImage = movie.posterPath?.let { "https://image.tmdb.org/t/p/w500/$it" }
                ?: "https://via.placeholder.com/300",
            overview = movie.overview ?: "No description available",
            voteAvg = movie.voteAverage ?: 0.0,
            genreId = movie.genreIds.firstOrNull() ?: 28,
            releaseDate = movie.releaseDate ?: "No date"
        )
    }
}