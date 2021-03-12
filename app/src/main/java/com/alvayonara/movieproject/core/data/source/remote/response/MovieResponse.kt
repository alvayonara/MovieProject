package com.alvayonara.movieproject.core.data.source.remote.response

import com.squareup.moshi.Json

data class MovieResponse(
    @field:Json(name = "id") val id: String? = "",
    @field:Json(name = "title") val title: String? = "",
    @field:Json(name = "release_date") val release_date: String? = "",
    @field:Json(name = "overview") val overview: String? = "",
    @field:Json(name = "poster_path") val poster_path: String? = ""
)