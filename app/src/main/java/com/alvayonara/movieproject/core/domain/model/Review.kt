package com.alvayonara.movieproject.core.domain.model

data class Review(
    val id: String,
    val authorDetail: AuthorDetail,
    val content: String
)

data class AuthorDetail(
    val name: String,
    val avatarPath: String,
    val rating: String
)