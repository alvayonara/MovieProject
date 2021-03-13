package com.alvayonara.movieproject.core.domain.usecase

import androidx.lifecycle.LiveData
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {

    override suspend fun getMovie(category: String): Flow<Result<Parser<List<MovieResponse>>>> =
        movieRepository.getMovie(category)

    override suspend fun getMovieById(movieId: String): Flow<Result<MovieResponse>> =
        movieRepository.getMovieById(movieId)

    override suspend fun getReview(movieId: String): Flow<Result<Parser<List<ReviewResponse>>>> =
        movieRepository.getReview(movieId)

    override fun getFavoriteMovie(): LiveData<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun checkIsFavoriteMovie(id: String): Flow<Int> =
        movieRepository.checkIsFavoriteMovie(id)

    override suspend fun insertFavoriteMovie(movie: Movie) =
        movieRepository.insertFavoriteMovie(movie)

    override suspend fun deleteFavoriteMovie(movie: Movie) =
        movieRepository.deleteFavoriteMovie(movie)
}