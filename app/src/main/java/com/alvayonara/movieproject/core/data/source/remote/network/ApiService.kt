package com.alvayonara.movieproject.core.data.source.remote.network

import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.utils.ApiUrl.MOVIES
import com.alvayonara.movieproject.core.utils.ApiUrl.MOVIE_BY_ID
import com.alvayonara.movieproject.core.utils.ApiUrl.REVIEW
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(MOVIES)
    suspend fun getMovie(
        @Path("movieCategory") movieCategory: String
    ): Response<Parser<List<MovieResponse>>>

    @GET(MOVIE_BY_ID)
    suspend fun getMovieById(
        @Path("movieId") movieId: String
    ): Response<MovieResponse>

    @GET(REVIEW)
    suspend fun getReview(
        @Path("movieId") movieId: String
    ): Response<Parser<List<ReviewResponse>>>
}