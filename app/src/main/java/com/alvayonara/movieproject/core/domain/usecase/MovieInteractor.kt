package com.alvayonara.movieproject.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.repository.IMovieRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {

    override fun getMovie(
        category: String,
        scope: CoroutineScope
    ): LiveData<Result<Parser<List<MovieResponse>>>> = movieRepository.getMovie(category, scope)

    override fun getMovieById(
        movieId: String,
        scope: CoroutineScope
    ): LiveData<Result<MovieResponse>> = movieRepository.getMovieById(movieId, scope)

    override fun getReview(
        movieId: String,
        scope: CoroutineScope
    ): LiveData<Result<Parser<List<ReviewResponse>>>> = movieRepository.getReview(movieId, scope)

    override fun getFavoriteMovie(): LiveData<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun checkIsFavoriteMovie(id: String): LiveData<Int> = movieRepository.checkIsFavoriteMovie(id)

    override suspend fun insertFavoriteMovie(movie: Movie) =
        movieRepository.insertFavoriteMovie(movie)

    override suspend fun deleteFavoriteMovie(movie: Movie) =
        movieRepository.deleteFavoriteMovie(movie)
}