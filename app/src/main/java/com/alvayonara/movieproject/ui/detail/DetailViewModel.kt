package com.alvayonara.movieproject.ui.detail

import androidx.lifecycle.*
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val movieId = MutableLiveData<String>()

    fun setMovieId(movieId: String) {
        this.movieId.value = movieId
    }

    val getMovieById = Transformations.switchMap(movieId) {
        movieUseCase.getMovieById(it, viewModelScope)
    }

    val getReview = Transformations.switchMap(movieId) {
        movieUseCase.getReview(it, viewModelScope)
    }

    val checkIsFavoriteMovie = Transformations.switchMap(movieId) {
        movieUseCase.checkIsFavoriteMovie(it)
    }

    fun insertFavoriteMovie(movie: Movie) =
        viewModelScope.launch { movieUseCase.insertFavoriteMovie(movie) }

    fun deleteFavoriteMovie(movie: Movie) =
        viewModelScope.launch { movieUseCase.deleteFavoriteMovie(movie) }
}