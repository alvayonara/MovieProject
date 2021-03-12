package com.alvayonara.movieproject.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alvayonara.movieproject.MyApplication
import com.alvayonara.movieproject.R
import com.alvayonara.movieproject.core.base.BaseActivity
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.ui.MovieAdapter
import com.alvayonara.movieproject.core.ui.ViewModelFactory
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_FAVORITE_IS_EMPTY
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_FAVORITE_IS_NOT_EMPTY
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_FAVORITE_LOADING
import com.alvayonara.movieproject.core.utils.hideLoading
import com.alvayonara.movieproject.core.utils.moveActivity
import com.alvayonara.movieproject.databinding.ActivityFavoriteBinding
import com.alvayonara.movieproject.ui.detail.DetailActivity
import javax.inject.Inject

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private lateinit var movieAdapter: MovieAdapter

    override val bindingInflater: (LayoutInflater) -> ActivityFavoriteBinding
        get() = ActivityFavoriteBinding::inflate

    override fun loadInjector() = (application as MyApplication).appComponent.inject(this)

    override fun setup() {
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupView() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.favorie_movie)
        }
    }

    override fun setupRecyclerView() {
        movieAdapter = MovieAdapter().apply {
            onItemClick = { data ->
                val bundle = Bundle().apply { putString(DetailActivity.MOVIE_ID, data) }
                moveActivity<DetailActivity>(bundle)
            }
        }

        with(binding.layoutMovieListFavorite.rvMovie) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = movieAdapter
        }
    }

    override fun subscribeViewModel() {
        binding.viewFlipperFavorite.displayedChild = UI_STATE_FAVORITE_LOADING
        favoriteViewModel.getFavoriteMovie.onLiveDataResult {
            movieAdapter.submitList(it)

            // Display favorite list.
            setDisplayFavorite(it)
        }
    }

    private fun setDisplayFavorite(data: List<Movie>) {
        // Set layout favorite list using ViewFlipper.
        binding.viewFlipperFavorite.displayedChild =
            if (data.isNotEmpty()) UI_STATE_FAVORITE_IS_NOT_EMPTY else UI_STATE_FAVORITE_IS_EMPTY
    }

    override fun releaseData() {
        binding.layoutMovieListFavorite.rvMovie.adapter = null
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