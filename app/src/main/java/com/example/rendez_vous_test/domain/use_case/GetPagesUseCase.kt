package com.example.rendez_vous_test.domain.use_case

import com.example.rendez_vous_test.domain.repository.TmdbRepository
import javax.inject.Inject

class GetPagesUseCase @Inject constructor(
    private val tmdbRepository: TmdbRepository
) {
    suspend operator fun invoke(): Int {
        return tmdbRepository.getPages()
    }
}
