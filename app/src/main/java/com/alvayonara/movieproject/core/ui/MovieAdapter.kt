package com.alvayonara.movieproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.alvayonara.movieproject.core.base.BaseAdapter
import com.alvayonara.movieproject.core.base.BaseViewHolder
import com.alvayonara.movieproject.core.domain.model.Movie
import com.alvayonara.movieproject.core.utils.cacheImage
import com.alvayonara.movieproject.core.utils.convertDateTime
import com.alvayonara.movieproject.databinding.ItemRowMovieBinding

class MovieAdapter : BaseAdapter<Movie, MovieAdapter.MovieViewHolder>(diffCallBack) {

    var onItemClick: ((String) -> Unit)? = null

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemRowMovieBinding
            .inflate(inflater, parent, false)
        return MovieViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null)
            holder.bindView(movie)

        holder.binding.cardViewMovie.setOnClickListener {
            onItemClick?.invoke(movie.id)
        }
    }

    inner class MovieViewHolder(
        root: View,
        val binding: ItemRowMovieBinding
    ) : BaseViewHolder<Movie>(root) {
        override fun bindView(element: Movie) {
            binding.apply {
                tvMovieTitle.text = element.title
                tvMovieReleaseDate.text = element.releaseDate.convertDateTime()
                tvMovieDescription.text = element.overview
                ivPosterMovie.cacheImage(element.posterPath)
            }
        }
    }
}