package com.example.rendez_vous_test.domain.use_case

import com.example.rendez_vous_test.domain.repository.TmdbRepository
import com.example.rendez_vous_test.domain.model.GenreIdName
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val tmdbRepository: TmdbRepository
) {
    suspend operator fun invoke(): List<GenreIdName> {
        return tmdbRepository.getGenres()
    }
}
