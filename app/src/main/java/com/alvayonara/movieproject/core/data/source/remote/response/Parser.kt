package com.alvayonara.movieproject.core.data.source.remote.response

import com.squareup.moshi.Json

data class Parser<T>(
    @field:Json(name = "page") val page: String?,
    @field:Json(name = "results") val results: T?,
    @field:Json(name = "total_pages") val totalPages: Int? = 0,
    @field:Json(name = "total_results") val totalResults: Int? = 0
)