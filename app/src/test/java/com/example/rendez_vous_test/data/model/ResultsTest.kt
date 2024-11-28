package com.example.rendez_vous_test.data.model

import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class ResultsModelTest {

    private val gson = Gson()

    @Test
    fun testSerializationOfResults() {
        val results = Results(
            adult = false,
            backdropPath = "/path/to/backdrop.jpg",
            genreIds = arrayListOf(28, 12),
            id = 123,
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "This is an overview",
            popularity = 8.5,
            posterPath = "/path/to/poster.jpg",
            releaseDate = "2024-01-01",
            title = "Movie Title",
            video = false,
            voteAverage = 7.8,
            voteCount = 1000
        )
        val json = gson.toJson(results)
        assertNotNull(json)
        assertTrue(json.contains("original_title"))
    }

    @Test
    fun testDeserializationOfResults() {
        val json = """
            {
                "adult": false,
                "backdrop_path": "/path/to/backdrop.jpg",
                "genre_ids": [28, 12],
                "id": 123,
                "original_language": "en",
                "original_title": "Original Title",
                "overview": "This is an overview",
                "popularity": 8.5,
                "poster_path": "/path/to/poster.jpg",
                "release_date": "2024-01-01",
                "title": "Movie Title",
                "video": false,
                "vote_average": 7.8,
                "vote_count": 1000
            }
        """
        val results = gson.fromJson(json, Results::class.java)
        assertNotNull(results)
        assertEquals(123, results.id)
        assertEquals("Movie Title", results.title)
        assertEquals(false, results.video)
    }

    @Test
    fun testDeserializationWithMissingField() {
        val json = """
            {
                "id": 456,
                "title": "Partial Movie"
            }
        """
        val results = gson.fromJson(json, Results::class.java)
        assertNotNull(results)
        assertEquals(456, results.id)
        assertEquals("Partial Movie", results.title)
        assertNull(results.overview) // Отсутствующее поле должно быть null
    }
}
