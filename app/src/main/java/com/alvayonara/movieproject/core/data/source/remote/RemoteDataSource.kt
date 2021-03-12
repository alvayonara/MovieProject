package com.alvayonara.movieproject.core.data.source.remote

import androidx.lifecycle.LiveData
import com.alvayonara.movieproject.core.base.BaseDataSource
import com.alvayonara.movieproject.core.base.resultLiveData
import com.alvayonara.movieproject.core.data.source.remote.network.ApiService
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {

    fun getMovie(
        movieCategory: String,
        scope: CoroutineScope
    ): LiveData<Result<Parser<List<MovieResponse>>>> =
        resultLiveData(scope) {
            safeApiCall { apiService.getMovie(movieCategory) }
        }

    fun getMovieById(movieId: String, scope: CoroutineScope): LiveData<Result<MovieResponse>> =
        resultLiveData(scope) {
            safeApiCall { apiService.getMovieById(movieId) }
        }

    fun getReview(
        movieId: String,
        scope: CoroutineScope
    ): LiveData<Result<Parser<List<ReviewResponse>>>> =
        resultLiveData(scope) {
            safeApiCall { apiService.getReview(movieId) }
        }
}