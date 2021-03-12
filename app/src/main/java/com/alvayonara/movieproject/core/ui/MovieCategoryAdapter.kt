package com.alvayonara.movieproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.alvayonara.movieproject.core.base.BaseAdapter
import com.alvayonara.movieproject.core.base.BaseViewHolder
import com.alvayonara.movieproject.core.domain.model.MovieCategory
import com.alvayonara.movieproject.databinding.ItemRowMovieCategoryBinding

class MovieCategoryAdapter :
    BaseAdapter<MovieCategory, MovieCategoryAdapter.MovieCategoryViewHolder>(diffCallBack) {

    var onItemClick: ((String) -> Unit)? = null

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<MovieCategory>() {
            override fun areItemsTheSame(oldItem: MovieCategory, newItem: MovieCategory): Boolean {
                return oldItem.movieCategoryName == newItem.movieCategoryName
            }

            override fun areContentsTheSame(
                oldItem: MovieCategory,
                newItem: MovieCategory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemRowMovieCategoryBinding
            .inflate(inflater, parent, false)
        return MovieCategoryViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: MovieCategoryViewHolder, position: Int) {
        val category = getItem(position)
        if (category != null)
            holder.bindView(category)

        holder.binding.layoutMovieCategory.setOnClickListener {
            onItemClick?.invoke(category.movieCategoryURL)
        }
    }

    inner class MovieCategoryViewHolder(
        root: View,
        val binding: ItemRowMovieCategoryBinding
    ) : BaseViewHolder<MovieCategory>(root) {
        override fun bindView(element: MovieCategory) {
            binding.tvMovieCategory.text = element.movieCategoryName
        }
    }
}