package com.alvayonara.movieproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.alvayonara.movieproject.core.base.BaseAdapter
import com.alvayonara.movieproject.core.base.BaseViewHolder
import com.alvayonara.movieproject.core.domain.model.Review
import com.alvayonara.movieproject.core.utils.cacheImage
import com.alvayonara.movieproject.databinding.ItemRowMovieReviewBinding

class ReviewAdapter : BaseAdapter<Review, ReviewAdapter.ReviewViewHolder>(diffCallBack) {

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemRowMovieReviewBinding
            .inflate(inflater, parent, false)
        return ReviewViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        if (review != null)
            holder.bindView(review)
    }

    inner class ReviewViewHolder(
        root: View,
        private val binding: ItemRowMovieReviewBinding
    ) : BaseViewHolder<Review>(root) {
        override fun bindView(element: Review) {
            binding.apply {
                ivReviewAvatar.cacheImage(element.authorDetail.avatarPath)
                tvReviewAuthor.text = element.authorDetail.name
                tvReviewRating.text = element.authorDetail.rating
                tvReviewContent.text = element.content
            }
        }
    }
}