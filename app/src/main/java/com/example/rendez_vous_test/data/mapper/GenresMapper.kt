package com.example.rendez_vous_test.data.mapper

import com.example.rendez_vous_test.data.model.Genre
import com.example.rendez_vous_test.domain.model.GenreIdName

class GenresMapper {
    fun map(genre: Genre): GenreIdName {
        return GenreIdName(
            id = genre.id,
            name = genre.name ?: "Undetected"
        )
    }
}