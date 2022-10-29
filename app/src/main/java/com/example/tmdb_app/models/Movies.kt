package com.example.tmdb_app.models

data class Movies(
    val page: Int? = null,
    val results: List<MovieResume> = listOf(),
    val total_pages: Int? = null,
    val total_results: Int? = null
)