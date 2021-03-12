package com.alvayonara.movieproject.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alvayonara.movieproject.core.data.source.local.LocalDataSource
import com.alvayonara.movieproject.core.data.source.local.entity.MovieEntity
import com.alvayonara.movieproject.core.data.source.remote.RemoteDataSource
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.network.Result.Status.*
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.repository.IMovieRepository
import com.alvayonara.movieproject.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {

    override fun getMovie(
        category: String,
        scope: CoroutineScope
    ): LiveData<Result<Parser<List<MovieResponse>>>> = remoteDataSource.getMovie(category, scope)

    override fun getMovieById(
        movieId: String,
        scope: CoroutineScope
    ): LiveData<Result<MovieResponse>> = remoteDataSource.getMovieById(movieId, scope)

    override fun getReview(
        movieId: String,
        scope: CoroutineScope
    ): LiveData<Result<Parser<List<ReviewResponse>>>> = remoteDataSource.getReview(movieId, scope)

    override fun getFavoriteMovie(): LiveData<List<Movie>> =
        localDataSource.getFavoriteMovie().map {
            DataMapper.mapMovieDetailEntitiesToDomain(it)
        }

    override fun checkIsFavoriteMovie(id: String): LiveData<Int> =
        localDataSource.checkIsFavoriteMovie(id)

    override suspend fun insertFavoriteMovie(movie: Movie) =
        localDataSource.insertFavoriteMovie(DataMapper.mapMovieDomainToEntity(movie))

    override suspend fun deleteFavoriteMovie(movie: Movie) =
        localDataSource.deleteFavoriteMovie(DataMapper.mapMovieDomainToEntity(movie))
}
