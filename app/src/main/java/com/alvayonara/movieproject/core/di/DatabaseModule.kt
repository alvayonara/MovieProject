package com.alvayonara.movieproject.core.di

import android.content.Context
import androidx.room.Room
import com.alvayonara.movieproject.core.data.source.local.room.MovieDao
import com.alvayonara.movieproject.core.data.source.local.room.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): MovieRoomDatabase = Room.databaseBuilder(
        context,
        MovieRoomDatabase::class.java, "Movie.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(database: MovieRoomDatabase): MovieDao = database.movieDao()
}