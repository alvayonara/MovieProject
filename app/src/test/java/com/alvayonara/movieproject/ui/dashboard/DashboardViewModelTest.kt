package com.alvayonara.movieproject.ui.dashboard

import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.domain.usecase.MovieUseCase
import com.alvayonara.movieproject.core.utils.MovieDataDummy
import com.alvayonara.movieproject.utils.BaseUnitTest
import com.alvayonara.movieproject.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DashboardViewModelTest: BaseUnitTest() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private val movieUseCase: MovieUseCase = mock()
    private val movies = mock<Parser<List<MovieResponse>>>()
    private val categoryId = MovieDataDummy.generateMovieCategory().first().movieCategoryURL

    private val expected = Result.success(movies)
    private val exception = "Something went wrong"
    private val error = Result.error(message = exception, data = null)

    @Before
    fun setUp() {
        dashboardViewModel = DashboardViewModel(movieUseCase)
        dashboardViewModel.setMovieCategory(categoryId)
    }

    @Test
    fun getMoviesFromUseCase() = runBlockingTest {
        mockSuccessfulCase()

        dashboardViewModel.getMovie.getValueForTest()

        verify(movieUseCase, times(1)).getMovie(categoryId)
    }

    @Test
    fun `Should successfully to get movie by category`() = runBlockingTest {
        mockSuccessfulCase()

        Assert.assertEquals(expected, dashboardViewModel.getMovie.getValueForTest())
    }

    @Test
    fun `Should error to get movie by category`() {
        mockErrorCase()

        Assert.assertEquals(exception, dashboardViewModel.getMovie.getValueForTest()!!.message)
    }

    private fun mockSuccessfulCase() {
        runBlocking {
            whenever(movieUseCase.getMovie(categoryId)).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
    }

    private fun mockErrorCase() {
        runBlocking {
            whenever(movieUseCase.getMovie(categoryId)).thenReturn(
                flow {
                    emit(error)
                }
            )
        }
    }
}