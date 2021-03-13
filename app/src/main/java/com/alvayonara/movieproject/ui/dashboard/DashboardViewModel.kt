package com.alvayonara.movieproject.ui.dashboard

import androidx.lifecycle.*
import com.alvayonara.movieproject.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val movieCategory = MutableLiveData<String>()

    fun setMovieCategory(movieCategory: String) {
        this.movieCategory.value = movieCategory
    }

    val getMovie = movieCategory.switchMap {
        liveData {
            emitSource(movieUseCase.getMovie(it).asLiveData())
        }
    }
}