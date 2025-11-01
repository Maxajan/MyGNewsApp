package com.example.my_gnews.data.network.dto

data class GNewsResponse (

    val totalArticles: Int,
    val articles: List<Article>
    )

data class Article (
    val id: String,
    val title: String?,
    val description: String?,
    val content: String?,
    val url: String,
    val image: String?,
    val publishedAt: String?,
    val source: Source
)
data class Source(
    val id: String,
    val name: String,
    val url: String
)