package com.alvayonara.movieproject.core.utils

import com.alvayonara.movieproject.core.data.source.local.entity.MovieEntity
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.AuthorDetail
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.model.Review

object DataMapper {

    /**
     * Get movie list
     */
    fun mapMovieListResponsesToDomain(input: MovieResponse): Movie = input.let {
        Movie(
            id = it.id.orEmpty(),
            title = it.title.orEmpty(),
            releaseDate = it.release_date.orEmpty(),
            overview = it.overview.orEmpty(),
            posterPath = it.poster_path.orEmpty()
        )
    }

    /**
     * Get movie detail
     */
    fun mapMovieDetailResponseToDomain(input: MovieResponse): Movie = input.let {
        Movie(
            id = it.id.orEmpty(),
            title = it.title.orEmpty(),
            releaseDate = it.release_date.orEmpty(),
            overview = it.overview.orEmpty(),
            posterPath = it.poster_path.orEmpty()
        )
    }

    /**
     * Get favorite movies
     */
    fun mapMovieDetailEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title.orEmpty(),
                releaseDate = it.release_date.orEmpty(),
                overview = it.overview.orEmpty(),
                posterPath = it.poster_path.orEmpty()
            )
        }

    /**
     * Insert favorite movie
     */
    fun mapMovieDomainToEntity(input: Movie) = input.let {
        MovieEntity(
            id = it.id,
            title = it.title,
            release_date = it.releaseDate,
            overview = it.overview,
            poster_path = it.posterPath
        )
    }

    /**
     * Get review movie
     */
    fun mapReviewResponsesToDomain(input: List<ReviewResponse>): List<Review> {
        val reviewList = ArrayList<Review>()
        input.map {
            val review = Review(
                id = it.id.orEmpty(),
                authorDetail = it.author_details.let { data ->
                    AuthorDetail(
                        name = if (data?.name.isNullOrBlank()) "Anonymous" else data?.name.orEmpty(),
                        avatarPath = data?.avatar_path.orEmpty(),
                        rating = data?.rating ?: "0.0"
                    )
                },
                content = it.content.orEmpty()
            )
            reviewList.add(review)
        }
        return reviewList
    }
}