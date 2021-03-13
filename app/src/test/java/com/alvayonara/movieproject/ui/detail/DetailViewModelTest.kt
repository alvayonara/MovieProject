package com.alvayonara.movieproject.ui.detail

import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
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
class DetailViewModelTest: BaseUnitTest() {

    private lateinit var detailViewModel: DetailViewModel

    private val movieUseCase: MovieUseCase = mock()
    private val movie = mock<MovieResponse>()
    private val review = mock<Parser<List<ReviewResponse>>>()
    private val movieId = MovieDataDummy.generateRemoteDummyMovies().first().id

    private val expectedMovie = Result.success(movie)
    private val expectedReview = Result.success(review)

    private val exception = "Something went wrong"
    private val error = Result.error(message = exception, data = null)

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(movieUseCase)
        detailViewModel.setMovieId(movieId.orEmpty())
    }

    /**
     * Movie by id
     */
    @Test
    fun getMovieByIdFromUseCase() = runBlockingTest {
        mockMovieByIdSuccessfulCase()

        detailViewModel.getMovieById.getValueForTest()

        verify(movieUseCase, times(1)).getMovieById(movieId.orEmpty())
    }

    @Test
    fun `Should successfully to get movie by id`() = runBlockingTest {
        mockMovieByIdSuccessfulCase()

        Assert.assertEquals(expectedMovie, detailViewModel.getMovieById.getValueForTest())
    }

    @Test
    fun `Should error to get movie by id`() {
        mockMovieByIdErrorCase()

        Assert.assertEquals(exception, detailViewModel.getMovieById.getValueForTest()!!.message)
    }

    private fun mockMovieByIdSuccessfulCase() {
        runBlocking {
            whenever(movieUseCase.getMovieById(movieId.orEmpty())).thenReturn(
                flow {
                    emit(expectedMovie)
                }
            )
        }
    }

    private fun mockMovieByIdErrorCase() {
        runBlocking {
            whenever(movieUseCase.getMovieById(movieId.orEmpty())).thenReturn(
                flow {
                    emit(error)
                }
            )
        }
    }

    /**
     * Review
     */
    @Test
    fun getReviewFromUseCase() = runBlockingTest {
        mockReviewSuccessfulCase()

        detailViewModel.getReview.getValueForTest()

        verify(movieUseCase, times(1)).getReview(movieId.orEmpty())
    }

    @Test
    fun `Should successfully to get review by id`() = runBlockingTest {
        mockReviewSuccessfulCase()

        Assert.assertEquals(expectedReview, detailViewModel.getReview.getValueForTest())
    }

    @Test
    fun `Should error to get review by id`() {
        mockReviewErrorCase()

        Assert.assertEquals(exception, detailViewModel.getReview.getValueForTest()!!.message)
    }

    private fun mockReviewSuccessfulCase() {
        runBlocking {
            whenever(movieUseCase.getReview(movieId.orEmpty())).thenReturn(
                flow {
                    emit(expectedReview)
                }
            )
        }
    }

    private fun mockReviewErrorCase() {
        runBlocking {
            whenever(movieUseCase.getReview(movieId.orEmpty())).thenReturn(
                flow {
                    emit(error)
                }
            )
        }
    }
}