package com.alvayonara.movieproject.core.data.source.local

import androidx.lifecycle.LiveData
import com.alvayonara.movieproject.core.base.BaseDataSource
import com.alvayonara.movieproject.core.data.source.local.entity.MovieEntity
import com.alvayonara.movieproject.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao): BaseDataSource() {

    fun getFavoriteMovie(): LiveData<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun checkIsFavoriteMovie(id: String): Flow<Int> = movieDao.checkIsFavoriteMovie(id)

    suspend fun insertFavoriteMovie(movieEntity: MovieEntity) =
        movieDao.insertFavoriteMovie(movieEntity)

    suspend fun deleteFavoriteMovie(movieEntity: MovieEntity) =
        movieDao.deleteFavoriteMovie(movieEntity)
}