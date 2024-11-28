package com.example.rendez_vous_test.domain.use_case

import com.example.rendez_vous_test.domain.repository.TmdbRepository
import com.example.rendez_vous_test.domain.model.Movie
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val tmdbRepository: TmdbRepository
) {
    suspend operator fun invoke(page: Int): List<Movie> {
        return tmdbRepository.getMovies(page)
    }
}
