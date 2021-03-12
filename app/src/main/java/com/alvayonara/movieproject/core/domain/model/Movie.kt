package com.alvayonara.movieproject.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String
) : Parcelable