package com.alvayonara.movieproject.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvayonara.movieproject.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val movieCategory = MutableLiveData<String>()

    fun setMovieCategory(movieCategory: String) {
        this.movieCategory.value = movieCategory
    }

    val getMovie = Transformations.switchMap(movieCategory) {
        movieUseCase.getMovie(it, viewModelScope)
    }
}