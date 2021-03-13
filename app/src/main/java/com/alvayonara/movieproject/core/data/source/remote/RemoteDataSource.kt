package com.alvayonara.movieproject.core.data.source.remote

import com.alvayonara.movieproject.core.base.BaseDataSource
import com.alvayonara.movieproject.core.data.source.remote.network.ApiService
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getMovie(movieCategory: String): Flow<Result<Parser<List<MovieResponse>>>> = flow {
        emit(Result.loading(null))
        emit(safeApiCall { apiService.getMovie(movieCategory) })
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieById(movieId: String): Flow<Result<MovieResponse>> = flow {
        emit(Result.loading(null))
        emit(safeApiCall { apiService.getMovieById(movieId) })
    }.flowOn(Dispatchers.IO)

    suspend fun getReview(movieId: String): Flow<Result<Parser<List<ReviewResponse>>>> = flow {
        emit(Result.loading(null))
        emit(safeApiCall { apiService.getReview(movieId) })
    }.flowOn(Dispatchers.IO)
}