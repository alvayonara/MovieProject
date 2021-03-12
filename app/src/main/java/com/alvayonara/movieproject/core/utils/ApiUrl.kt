package com.alvayonara.movieproject.core.utils

import com.alvayonara.movieproject.BuildConfig.TMDB_API_KEY

object ApiUrl {

    /**
     * Movie URL
     */
    private const val API_KEY = "?api_key=${TMDB_API_KEY}"
    const val MOVIES = "{movieCategory}${API_KEY}"
    const val MOVIE_BY_ID = "{movieId}${API_KEY}"
    const val REVIEW = "{movieId}/reviews${API_KEY}"
}