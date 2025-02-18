package com.example.rendez_vous_test.domain.model

data class Movie(
    val id: Int?,
    val title: String?,
    val movieImage: String?,
    val overview: String?,
    val voteAvg: Double?,
    val genreId: Int?,
    val releaseDate: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Movie) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}