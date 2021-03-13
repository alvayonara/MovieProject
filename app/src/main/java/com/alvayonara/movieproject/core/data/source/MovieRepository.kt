package com.alvayonara.movieproject.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.alvayonara.movieproject.core.data.source.local.LocalDataSource
import com.alvayonara.movieproject.core.data.source.remote.RemoteDataSource
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.repository.IMovieRepository
import com.alvayonara.movieproject.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {

    override suspend fun getMovie(category: String): Flow<Result<Parser<List<MovieResponse>>>> =
        remoteDataSource.getMovie(category)

    override suspend fun getMovieById(movieId: String): Flow<Result<MovieResponse>> =
        remoteDataSource.getMovieById(movieId)

    override suspend fun getReview(movieId: String): Flow<Result<Parser<List<ReviewResponse>>>> =
        remoteDataSource.getReview(movieId)

    override fun getFavoriteMovie(): LiveData<List<Movie>> =
        localDataSource.getFavoriteMovie().map {
            DataMapper.mapMovieDetailEntitiesToDomain(it)
        }

    override fun checkIsFavoriteMovie(id: String): Flow<Int> =
        localDataSource.checkIsFavoriteMovie(id)

    override suspend fun insertFavoriteMovie(movie: Movie) =
        localDataSource.insertFavoriteMovie(DataMapper.mapMovieDomainToEntity(movie))

    override suspend fun deleteFavoriteMovie(movie: Movie) =
        localDataSource.deleteFavoriteMovie(DataMapper.mapMovieDomainToEntity(movie))
}
