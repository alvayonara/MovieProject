package com.alvayonara.movieproject.core.utils

import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.model.MovieCategory
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_NOW_PLAYING_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_NOW_PLAYING_URL
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_POPULAR_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_POPULAR_URL
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_TOP_RATED_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_TOP_RATED_URL
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_UPCOMING_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_UPCOMING_URL

object MovieDataDummy {

    fun generateMovieCategory(): List<MovieCategory> {
        val movieCategories = ArrayList<MovieCategory>()
        movieCategories.add(
            MovieCategory(MOVIE_POPULAR_NAME, MOVIE_POPULAR_URL)
        )
        movieCategories.add(
            MovieCategory(MOVIE_UPCOMING_NAME, MOVIE_UPCOMING_URL)
        )
        movieCategories.add(
            MovieCategory(MOVIE_TOP_RATED_NAME, MOVIE_TOP_RATED_URL)
        )
        movieCategories.add(
            MovieCategory(MOVIE_NOW_PLAYING_NAME, MOVIE_NOW_PLAYING_URL)
        )
        return movieCategories
    }

    fun generateRemoteDummyMovies(): List<MovieResponse> {
        val movies = ArrayList<MovieResponse>()
        movies.add(
            MovieResponse(
                id = "181812",
                title = "Star Wars: The Rise of Skywalker",
                release_date = "2019-12-18",
                overview = "The surviving Resistance faces the First Order once again as the journey of Rey, Finn and Poe Dameron continues. With the power and knowledge of generations behind them, the final battle begins.",
                poster_path = "o86DbpburjxrqAzEDhXZcyE8pDb.png"
            )
        )
        movies.add(
            MovieResponse(
                id = "920",
                title = "Cars",
                release_date = "2006-06-08",
                overview = "Lightning McQueen, a hotshot rookie race car driven to succeed, discovers that life is about the journey, not the finish line, when he finds himself unexpectedly detoured in the sleepy Route 66 town of Radiator Springs. On route across the country to the big Piston Cup Championship in California to compete against two seasoned pros, McQueen gets to know the town's offbeat characters.",
                poster_path = "uq3N2SFj1Y06zA6LzCQPkmBdaaE.jpg",
            )
        )
        return movies
    }

    fun mapDummyMoviesResponsesToDomain(input: MovieResponse) = input.let {
        Movie(
            id = it.id.orEmpty(),
            title = it.title.orEmpty(),
            releaseDate = it.release_date.orEmpty(),
            overview = it.overview.orEmpty(),
            posterPath = it.poster_path.orEmpty()
        )
    }
}