package com.alvayonara.movieproject.core.data.source.remote.response

import com.squareup.moshi.Json

data class ReviewResponse(
    @field:Json(name = "id") val id: String? = "",
    @field:Json(name = "author_details") val author_details: AuthorDetailResponse? = null,
    @field:Json(name = "content") val content: String? = ""
)

data class AuthorDetailResponse(
    @field:Json(name = "name") val name: String? = "",
    @field:Json(name = "avatar_path") val avatar_path: String? = "",
    @field:Json(name = "rating") val rating: String? = ""
)