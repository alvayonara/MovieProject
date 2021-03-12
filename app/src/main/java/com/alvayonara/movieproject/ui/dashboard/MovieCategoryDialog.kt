package com.alvayonara.movieproject.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.movieproject.core.base.BaseBottomSheetDialog
import com.alvayonara.movieproject.core.domain.model.MovieCategory
import com.alvayonara.movieproject.core.ui.MovieCategoryAdapter
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_NOW_PLAYING_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_NOW_PLAYING_URL
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_POPULAR_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_POPULAR_URL
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_TOP_RATED_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_TOP_RATED_URL
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_UPCOMING_NAME
import com.alvayonara.movieproject.core.utils.ConstMovieCategory.MOVIE_UPCOMING_URL
import com.alvayonara.movieproject.databinding.ViewBottomSheetMovieCategoryBinding

class MovieCategoryDialog : BaseBottomSheetDialog<ViewBottomSheetMovieCategoryBinding>() {

    private var listener: MovieCategoryListener? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBottomSheetMovieCategoryBinding
        get() = ViewBottomSheetMovieCategoryBinding::inflate

    override fun setup() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val movieCategoryAdapter = MovieCategoryAdapter().apply {
            onItemClick = {
                listener?.onMovieCategoryClicked(it)
                dismiss()
            }
        }

        with(binding.rvMovieCategory) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = movieCategoryAdapter
        }

        movieCategoryAdapter.submitList(initMovieCategory())
    }

    private fun initMovieCategory() = listOf(
        MovieCategory(MOVIE_POPULAR_NAME, MOVIE_POPULAR_URL),
        MovieCategory(MOVIE_UPCOMING_NAME, MOVIE_UPCOMING_URL),
        MovieCategory(MOVIE_TOP_RATED_NAME, MOVIE_TOP_RATED_URL),
        MovieCategory(MOVIE_NOW_PLAYING_NAME, MOVIE_NOW_PLAYING_URL)
    )

    fun setListener(listener: MovieCategoryListener) {
        this.listener = listener
    }

    fun removeListener() {
        listener = null
    }

    interface MovieCategoryListener {
        fun onMovieCategoryClicked(name: String)
    }
}