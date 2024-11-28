package com.example.rendez_vous_test.data.mapper

import com.example.rendez_vous_test.data.model.PopularMovies

class PagesMapper {
    fun map(moviePage: PopularMovies) : Int {
        return moviePage.totalPages?.toInt() ?: 1
    }
}