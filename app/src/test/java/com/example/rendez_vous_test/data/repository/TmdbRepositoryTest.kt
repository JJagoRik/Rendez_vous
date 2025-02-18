package com.example.rendez_vous_test.data.repository

import com.example.rendez_vous_test.data.datasource.remote.TmdbApi
import com.example.rendez_vous_test.data.mapper.GenresMapper
import com.example.rendez_vous_test.data.mapper.PagesMapper
import com.example.rendez_vous_test.data.mapper.ResultsMapper
import com.example.rendez_vous_test.data.model.PopularMovies
import com.example.rendez_vous_test.data.model.Results
import com.example.rendez_vous_test.domain.model.Movie
import com.example.rendez_vous_test.domain.repository.TmdbRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TmdbRepositoryImplTest {

    private lateinit var tmdbApi: TmdbApi
    private lateinit var resultsMapper: ResultsMapper
    private lateinit var genresMapper: GenresMapper
    private lateinit var pagesMapper: PagesMapper
    private lateinit var tmdbRepository: TmdbRepository

    @Before
    fun setup() {
        tmdbApi = mockk()
        resultsMapper = mockk()
        genresMapper = mockk()
        pagesMapper = mockk()

        coEvery { pagesMapper.map(any()) } returns 5

        tmdbRepository = TmdbRepositoryImpl(tmdbApi, resultsMapper, genresMapper, pagesMapper)
    }


    @Test
    fun getMoviesTest() = runBlocking {
        val mockMovie = Results(
            id = 1,
            title = "Movie 1",
            posterPath = "image_url",
            overview = "Movie overview",
            voteAverage = 8.5,
            genreIds = arrayListOf(28)
        )

        val mockMovies = listOf(mockMovie)
        val popularMovies = PopularMovies(results = ArrayList(mockMovies))
        val mappedMovie = Movie(
            id = 1,
            title = "Movie 1",
            movieImage = "https://image.tmdb.org/t/p/w500/image_url",
            overview = "Movie overview",
            voteAvg = 8.5,
            genreId = 28,
            releaseDate = "",
        )

        coEvery { tmdbApi.getMovies(API_KEY, 1) } returns popularMovies
        coEvery { resultsMapper.map(mockMovie) } returns mappedMovie

        val result = tmdbRepository.getMovies(1)

        coVerify { tmdbApi.getMovies(API_KEY, 1) }
        assert(result == listOf(mappedMovie))
    }




    @Test
    fun getPagesTest() = runBlocking {
        val popularMovies = PopularMovies(totalPages = 5)
        coEvery { tmdbApi.getPages(any()) } returns popularMovies

        val result = tmdbRepository.getPages()

        coVerify { tmdbApi.getPages(any()) }
        assert(result == 5)
    }

    @Test
    fun searchMoviesTest() = runBlocking {
        val query = "Movie 1"
        val mockMovie = Results(
            id = 1,
            title = "Movie 1",
            posterPath = "image_url",
            overview = "Movie overview",
            voteAverage = 8.5,
            genreIds = arrayListOf(28)
        )

        val mockMovies = listOf(mockMovie)
        val searchResult = PopularMovies(results = ArrayList(mockMovies))
        val mappedMovie = Movie(
            id = 1,
            title = "Movie 1",
            movieImage = "https://image.tmdb.org/t/p/w500/image_url",
            overview = "Movie overview",
            voteAvg = 8.5,
            genreId = 28,
            releaseDate = "",
        )

        coEvery { tmdbApi.searchMovies(API_KEY, query) } returns searchResult
        coEvery { resultsMapper.map(mockMovie) } returns mappedMovie

        val result = tmdbRepository.searchMovies(query)

        coVerify { tmdbApi.searchMovies(API_KEY, query) }
        assert(result == listOf(mappedMovie))
    }


    @Test
    fun getMoviesErrorTest() {
        coEvery { tmdbApi.getMovies(any(), any()) } throws Exception("API error")

        runBlocking {
            try {
                tmdbRepository.getMovies(1)
            } catch (e: Exception) {
                assert(e is Exception)
            }
        }
    }
}
