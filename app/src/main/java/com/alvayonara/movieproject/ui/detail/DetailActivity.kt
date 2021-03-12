package com.alvayonara.movieproject.ui.detail

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alvayonara.movieproject.MyApplication
import com.alvayonara.movieproject.R
import com.alvayonara.movieproject.core.base.BaseActivity
import com.alvayonara.movieproject.core.data.source.remote.network.Result.Status.*
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.domain.model.Review
import com.alvayonara.movieproject.core.ui.ReviewAdapter
import com.alvayonara.movieproject.core.ui.ViewModelFactory
import com.alvayonara.movieproject.core.utils.*
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_DASHBOARD_ERROR
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_DASHBOARD_LOADING
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_REVIEW_IS_EMPTY
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_REVIEW_IS_NOT_EMPTY
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_DASHBOARD_SUCCESS
import com.alvayonara.movieproject.databinding.ActivityDetailBinding
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val detailViewModel: DetailViewModel by viewModels {
        factory
    }

    private lateinit var reviewAdapter: ReviewAdapter
    private var movieId = ""

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityDetailBinding
        get() = ActivityDetailBinding::inflate

    override fun loadInjector() = (application as MyApplication).appComponent.inject(this)

    override fun setup() {
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupView() {
        movieId = getBundle()?.getString(MOVIE_ID).orEmpty()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.swipeRefresh.setOnRefreshListener(this)

        binding.layoutErrorFetchData.btnRetry.setOnClickListener {
            detailViewModel.setMovieId(movieId)
        }
    }

    override fun setupRecyclerView() {
        reviewAdapter = ReviewAdapter()
        with(binding.layoutDetailContent.rvMovieReview) {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            adapter = reviewAdapter
        }
    }

    override fun subscribeViewModel() {
        detailViewModel.setMovieId(movieId)

        detailViewModel.getMovieById.onLiveDataResult {
            binding.viewFlipperDetail.displayedChild = when (it.status) {
                LOADING -> {
                    binding.layoutDetailContent.ivMovieFavoriteButton.gone()
                    UI_STATE_DASHBOARD_LOADING
                }
                SUCCESS -> {
                    val data = it.body?.let { data ->
                        DataMapper.mapMovieDetailResponseToDomain(data)
                    }
                    // Check is movie favorite.
                    checkIsFavoriteMovie(data)

                    // Display detail movie.
                    populateDetailMovie(data)
                    UI_STATE_DASHBOARD_SUCCESS
                }
                ERROR -> {
                    binding.layoutDetailContent.ivMovieFavoriteButton.gone()
                    UI_STATE_DASHBOARD_ERROR
                }
            }
        }

        detailViewModel.getReview.onLiveDataResult {
            when (it.status) {
                LOADING -> {
                }
                SUCCESS -> {
                    binding.swipeRefresh.hideLoading()
                    val data = it.body?.results?.let { data ->
                        DataMapper.mapReviewResponsesToDomain(data)
                    }
                    reviewAdapter.submitList(data)

                    // Display review list.
                    setDisplayReview(data)
                }
                ERROR -> {
                }
            }
        }
    }

    private fun setDisplayReview(data: List<Review>?) {
        if (data != null) {
            // Set layout review list using ViewFlipper.
            binding.layoutDetailContent.viewFlipperReview.displayedChild =
                if (data.isNotEmpty()) UI_STATE_REVIEW_IS_NOT_EMPTY else UI_STATE_REVIEW_IS_EMPTY
        }
    }

    private fun populateDetailMovie(data: Movie?) {
        data?.let {
            binding.layoutDetailContent.apply {
                supportActionBar?.title = it.title
                tvMovieTitleDetail.text = it.title
                tvMovieReleaseDateDetail.text = it.releaseDate.convertDateTime()
                tvMovieOverviewDetail.text = it.overview
                ivPosterMovieDetail.cacheImage(it.posterPath)
            }
        }
    }

    private fun checkIsFavoriteMovie(data: Movie?) {
        // Observe result to set status favorite icon.
        var isFavoriteMovie = false
        detailViewModel.checkIsFavoriteMovie.onLiveDataResult {
            it.let { favorite ->
                isFavoriteMovie = favorite > 0
                setStatusFavorite(isFavoriteMovie)
            }
        }

        // Set view to visible after observing status favorite.
        binding.layoutDetailContent.ivMovieFavoriteButton.visible()

        // Set click listener on button favorite.
        binding.layoutDetailContent.ivMovieFavoriteButton.setOnClickListener {
            if (data != null) {
                if (isFavoriteMovie)
                    detailViewModel.deleteFavoriteMovie(data)
                else
                    detailViewModel.insertFavoriteMovie(data)

                isFavoriteMovie = !isFavoriteMovie
                setStatusFavorite(isFavoriteMovie)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        binding.layoutDetailContent.ivMovieFavoriteButton.setImageDrawable(
            ContextCompat.getDrawable(
                this@DetailActivity,
                if (statusFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        )
    }

    override fun onRefresh() {
        detailViewModel.setMovieId(movieId)
        binding.swipeRefresh.hideLoading()
    }

    override fun releaseData() {
        binding.layoutDetailContent.rvMovieReview.adapter = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}