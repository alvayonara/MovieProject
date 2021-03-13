package com.alvayonara.movieproject.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alvayonara.movieproject.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getFavoriteMovie(): LiveData<List<MovieEntity>>

    @Query("SELECT COUNT(*) FROM movie WHERE id=:id")
    fun checkIsFavoriteMovie(id: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(movieEntity: MovieEntity)
}