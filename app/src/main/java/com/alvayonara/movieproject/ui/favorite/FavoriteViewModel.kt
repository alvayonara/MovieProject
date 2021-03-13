package com.alvayonara.movieproject.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alvayonara.movieproject.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {

    val getFavoriteMovie = liveData {
        emitSource(movieUseCase.getFavoriteMovie())
    }
}