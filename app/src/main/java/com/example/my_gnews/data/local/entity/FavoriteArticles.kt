package com.example.my_gnews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "favorite_article")
data class FavoriteArticle (
    @PrimaryKey val url: String,
    val title: String,
    val description: String,
    val image: String,
    val publishedAt: String
)
