package com.alvayonara.movieproject.data

import com.alvayonara.movieproject.core.data.source.MovieRepository
import com.alvayonara.movieproject.core.data.source.local.LocalDataSource
import com.alvayonara.movieproject.core.data.source.remote.RemoteDataSource
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import com.alvayonara.movieproject.core.data.source.remote.response.MovieResponse
import com.alvayonara.movieproject.core.data.source.remote.response.Parser
import com.alvayonara.movieproject.core.data.source.remote.response.ReviewResponse
import com.alvayonara.movieproject.core.utils.MovieDataDummy
import com.alvayonara.movieproject.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest : BaseUnitTest() {

    private lateinit var movieRepository: MovieRepository

    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()

    private val categoryId = MovieDataDummy.generateMovieCategory().first().movieCategoryURL
    private val movieId = MovieDataDummy.generateMovieCategory().first().movieCategoryURL

    private val movie = mock<MovieResponse>()
    private val movies = mock<Parser<List<MovieResponse>>>()
    private val review = mock<Parser<List<ReviewResponse>>>()

    private val expectedMovie = Result.success(movie)
    private val expectedMovies = Result.success(movies)
    private val expectedReview = Result.success(review)

    private val exception = "Something went wrong"
    private val error = Result.error(message = exception, data = null)

    @Before
    fun setUp() {
        movieRepository = MovieRepository(remoteDataSource, localDataSource)
    }

    /**
     * Movie by category
     */
    @Test
    fun getMovieFromRemoteSource() = runBlockingTest {
        mockMovieByCategorySuccessfulCase()

        movieRepository.getMovie(categoryId)

        verify(remoteDataSource, times(1)).getMovie(categoryId)
    }

    @Test
    fun `Should successfully to get movie by category`() = runBlockingTest {
        mockMovieByCategorySuccessfulCase()

        Assert.assertEquals(expectedMovies, movieRepository.getMovie(categoryId).first())
    }

    @Test
    fun `Should error to get movie by category`() = runBlockingTest {
        mockMovieByCategoryFailureCase()

        Assert.assertEquals(exception, movieRepository.getMovie(categoryId).first().message)
    }

    private suspend fun mockMovieByCategorySuccessfulCase() {
        whenever(remoteDataSource.getMovie(categoryId)).thenReturn(
            flow {
                emit(expectedMovies)
            }
        )
    }

    private suspend fun mockMovieByCategoryFailureCase() {
        whenever(remoteDataSource.getMovie(categoryId)).thenReturn(
            flow {
                emit(error)
            }
        )
    }

    /**
     * Movie by id
     */
    @Test
    fun getMovieByIdFromRemoteSource() = runBlockingTest {
        mockMovieByIdSuccessfulCase()

        movieRepository.getMovieById(movieId)

        verify(remoteDataSource, times(1)).getMovieById(movieId)
    }

    @Test
    fun `Should successfully to get movie by id`() = runBlockingTest {
        mockMovieByIdSuccessfulCase()

        Assert.assertEquals(expectedMovie, movieRepository.getMovieById(movieId).first())
    }

    @Test
    fun `Should error to get movie by id`() = runBlockingTest {
        mockMovieByIdFailureCase()

        Assert.assertEquals(exception, movieRepository.getMovieById(movieId).first().message)
    }

    private suspend fun mockMovieByIdSuccessfulCase() {
        whenever(remoteDataSource.getMovieById(movieId)).thenReturn(
            flow {
                emit(expectedMovie)
            }
        )
    }

    private suspend fun mockMovieByIdFailureCase() {
        whenever(remoteDataSource.getMovieById(movieId)).thenReturn(
            flow {
                emit(error)
            }
        )
    }

    /**
     * Review
     */
    @Test
    fun getReviewFromRemoteSource() = runBlockingTest {
        mockReviewSuccessfulCase()

        movieRepository.getReview(movieId)

        verify(remoteDataSource, times(1)).getReview(movieId)
    }

    @Test
    fun `Should successfully to get review by id`() = runBlockingTest {
        mockReviewSuccessfulCase()

        Assert.assertEquals(expectedReview, movieRepository.getReview(movieId).first())
    }

    @Test
    fun `Should error to get review by id`() = runBlockingTest {
        mockReviewFailureCase()

        Assert.assertEquals(exception, movieRepository.getReview(movieId).first().message)
    }

    private suspend fun mockReviewSuccessfulCase() {
        whenever(remoteDataSource.getReview(movieId)).thenReturn(
            flow {
                emit(expectedReview)
            }
        )
    }

    private suspend fun mockReviewFailureCase() {
        whenever(remoteDataSource.getReview(movieId)).thenReturn(
            flow {
                emit(error)
            }
        )
    }
}