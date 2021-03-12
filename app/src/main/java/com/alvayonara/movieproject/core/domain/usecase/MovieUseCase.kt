package com.alvayonara.movieproject.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import kotlinx.coroutines.CoroutineScope

interface MovieUseCase {

    fun getMovie(category: String, scope: CoroutineScope): LiveData<Result<Parser<List<MovieResponse>>>>

    fun getMovieById(movieId: String, scope: CoroutineScope): LiveData<Result<MovieResponse>>

    fun getReview(movieId: String, scope: CoroutineScope): LiveData<Result<Parser<List<ReviewResponse>>>>

    fun getFavoriteMovie(): LiveData<List<Movie>>

    fun checkIsFavoriteMovie(id: String): LiveData<Int>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}