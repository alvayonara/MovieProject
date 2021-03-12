package com.alvayonara.movieproject.di

import com.alvayonara.movieproject.core.domain.usecase.MovieInteractor
import com.alvayonara.movieproject.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}