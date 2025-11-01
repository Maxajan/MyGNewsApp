package com.example.my_gnews.data.mapper

import com.example.my_gnews.data.local.entity.FavoriteArticle
import com.example.my_gnews.data.network.dto.Article

fun Article.toFavoriteEntity(): FavoriteArticle {
    return FavoriteArticle(
        url = url ?: "no_url_${System.currentTimeMillis()}",
        title = title ?: "Untitled",
        description = description ?: "No description available",
        image = image ?: "https://picsum.photos/id/237/200/300\n",
        publishedAt = publishedAt ?: "Unknown date"
    )
}
