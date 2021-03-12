package com.alvayonara.movieproject.core.di

import com.alvayonara.movieproject.core.data.source.MovieRepository
import com.alvayonara.movieproject.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(movieRepository: MovieRepository): IMovieRepository
}