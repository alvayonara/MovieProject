package com.alvayonara.movieproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alvayonara.movieproject.MyApplication
import com.alvayonara.movieproject.R
import com.alvayonara.movieproject.core.base.BaseActivity
import com.alvayonara.movieproject.core.data.source.remote.network.Result.Status.*
import com.alvayonara.movieproject.core.ui.MovieAdapter
import com.alvayonara.movieproject.core.ui.ViewModelFactory
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_POPULAR_URL
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_DASHBOARD_ERROR
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_DASHBOARD_LOADING
import com.alvayonara.movieproject.core.utils.ConstUiState.UI_STATE_DASHBOARD_SUCCESS
import com.alvayonara.movieproject.core.utils.DataMapper
import com.alvayonara.movieproject.core.utils.hideLoading
import com.alvayonara.movieproject.core.utils.moveActivity
import com.alvayonara.movieproject.databinding.ActivityDashboardBinding
import com.alvayonara.movieproject.ui.detail.DetailActivity
import com.alvayonara.movieproject.ui.detail.DetailActivity.Companion.MOVIE_ID
import com.alvayonara.movieproject.ui.favorite.FavoriteActivity
import java.util.*
import javax.inject.Inject

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(),
    SwipeRefreshLayout.OnRefreshListener, MovieCategoryDialog.MovieCategoryListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val dashboardViewModel: DashboardViewModel by viewModels {
        factory
    }

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieCategoryDialog: MovieCategoryDialog

    /**
     * Set category Popular for initial fetch data
     */
    private var category: String = MOVIE_POPULAR_URL

    override val bindingInflater: (LayoutInflater) -> ActivityDashboardBinding
        get() = ActivityDashboardBinding::inflate

    override fun loadInjector() = (application as MyApplication).appComponent.inject(this)

    override fun setup() {
        setupDialog()
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupDialog() {
        movieCategoryDialog = MovieCategoryDialog()
        movieCategoryDialog.setListener(this)
    }

    override fun onMovieCategoryClicked(name: String) {
        category = name
        dashboardViewModel.setMovieCategory(name)
    }

    override fun setupView() {
        binding.swipeRefresh.setOnRefreshListener(this)

        binding.layoutErrorFetchData.btnRetry.setOnClickListener {
            dashboardViewModel.setMovieCategory(category)
        }

        binding.btnMovieCategory.setOnClickListener {
            movieCategoryDialog.show(supportFragmentManager, "")
        }
    }

    override fun setupRecyclerView() {
        movieAdapter = MovieAdapter().apply {
            onItemClick = { data ->
                val bundle = Bundle().apply { putString(MOVIE_ID, data) }
                moveActivity<DetailActivity>(bundle)
            }
        }

        with(binding.layoutMovieListDashboard.rvMovie) {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = movieAdapter
        }
    }

    override fun subscribeViewModel() {
        dashboardViewModel.setMovieCategory(category)
        dashboardViewModel.getMovie.onLiveDataResult {
            binding.viewFlipperDashboard.displayedChild = when (it.status) {
                LOADING -> UI_STATE_DASHBOARD_LOADING
                SUCCESS -> {
                    val data = it.body?.results?.map { data ->
                        DataMapper.mapMovieListResponsesToDomain(data)
                    }
                    movieAdapter.submitList(data)
                    UI_STATE_DASHBOARD_SUCCESS
                }
                ERROR -> UI_STATE_DASHBOARD_ERROR
            }
        }
    }

    override fun onRefresh() {
        dashboardViewModel.setMovieCategory(category)
        binding.swipeRefresh.hideLoading()
    }

    override fun releaseData() {
        binding.layoutMovieListDashboard.rvMovie.adapter = null
        movieCategoryDialog.removeListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                moveActivity<FavoriteActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}