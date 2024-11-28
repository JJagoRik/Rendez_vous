package com.example.rendez_vous_test.di

import com.example.rendez_vous_test.data.datasource.remote.TmdbApi
import com.example.rendez_vous_test.data.mapper.GenresMapper
import com.example.rendez_vous_test.data.mapper.PagesMapper
import com.example.rendez_vous_test.data.mapper.ResultsMapper
import com.example.rendez_vous_test.data.repository.TmdbRepositoryImpl
import com.example.rendez_vous_test.domain.repository.TmdbRepository
import com.example.rendez_vous_test.domain.use_case.GetGenresUseCase
import com.example.rendez_vous_test.domain.use_case.GetMoviesUseCase
import com.example.rendez_vous_test.domain.use_case.GetPagesUseCase
import com.example.rendez_vous_test.domain.use_case.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideTmdbApi(): TmdbApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideResultsMapper(): ResultsMapper = ResultsMapper()

    @Provides
    @Singleton
    fun provideGenresMapper(): GenresMapper = GenresMapper()

    @Provides
    @Singleton
    fun providePagesMapper(): PagesMapper = PagesMapper()

    @Provides
    @Singleton
    fun provideTmdbRepository(
        tmdbApi: TmdbApi,
        resultsMoviesMapper: ResultsMapper,
        genresMapper: GenresMapper,
        pagesMapper: PagesMapper
    ): TmdbRepository {
        return TmdbRepositoryImpl(tmdbApi, resultsMoviesMapper, genresMapper, pagesMapper)
    }

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(tmdbRepository: TmdbRepository): GetMoviesUseCase {
        return GetMoviesUseCase(tmdbRepository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(tmdbRepository: TmdbRepository): SearchMoviesUseCase {
        return SearchMoviesUseCase(tmdbRepository)
    }

    @Provides
    @Singleton
    fun provideGetGenresUseCase(tmdbRepository: TmdbRepository): GetGenresUseCase {
        return GetGenresUseCase(tmdbRepository)
    }

    @Provides
    @Singleton
    fun provideGetPagesUseCase(tmdbRepository: TmdbRepository): GetPagesUseCase {
        return GetPagesUseCase(tmdbRepository)
    }
}
