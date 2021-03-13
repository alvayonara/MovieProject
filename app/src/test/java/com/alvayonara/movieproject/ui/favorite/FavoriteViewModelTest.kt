package com.alvayonara.movieproject.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.usecase.MovieUseCase
import com.alvayonara.movieproject.core.utils.MovieDataDummy
import com.alvayonara.movieproject.utils.BaseUnitTest
import com.alvayonara.movieproject.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times

class FavoriteViewModelTest : BaseUnitTest() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    private val movieUseCase: MovieUseCase = mock()
    private val movieDummy = MovieDataDummy.generateRemoteDummyMovies().map {
        MovieDataDummy.mapDummyMoviesResponsesToDomain(it)
    }
    private val observer = mock<Observer<List<Movie>>>()

    @Before
    fun setUp() {
        favoriteViewModel = FavoriteViewModel(movieUseCase)
    }

    @Test
    fun getFavoriteMovie() {
        val favoriteMovies = MutableLiveData<List<Movie>>()
        favoriteMovies.value = movieDummy

        whenever(movieUseCase.getFavoriteMovie()).thenReturn(favoriteMovies)

        val movieEntities = favoriteViewModel.getFavoriteMovie.getValueForTest()
        verify(movieUseCase).getFavoriteMovie()
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(2, movieEntities?.size)

        favoriteViewModel.getFavoriteMovie.observeForever(observer)
        verify(observer).onChanged(movieDummy)
    }
}