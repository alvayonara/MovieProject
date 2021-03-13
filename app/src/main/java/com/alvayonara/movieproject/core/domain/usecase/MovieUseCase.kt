package com.alvayonara.movieproject.core.domain.usecase

import androidx.lifecycle.LiveData
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    suspend fun getMovie(category: String): Flow<Result<Parser<List<MovieResponse>>>>

    suspend fun getMovieById(movieId: String): Flow<Result<MovieResponse>>

    suspend fun getReview(movieId: String): Flow<Result<Parser<List<ReviewResponse>>>>

    fun getFavoriteMovie(): LiveData<List<Movie>>

    fun checkIsFavoriteMovie(id: String): Flow<Int>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}