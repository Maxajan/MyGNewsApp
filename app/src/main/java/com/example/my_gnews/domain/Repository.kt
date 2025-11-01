package com.example.my_gnews.domain
import com.example.my_gnews.data.local.entity.FavoriteArticle
import com.example.my_gnews.data.network.dto.Article
import com.example.my_gnews.data.network.dto.GNewsResponse
import com.example.my_gnews.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines (
    category: String,
    country: String,
    language: String,
    apiKey: String
    ) : Flow<Resource<List<Article>>>

    fun getSearchNews (
        query: String,
        country: String,
        language: String,
        apiKey: String
    ) :  Flow<Resource<List<Article>>>

    fun getAllFavoriteArticles(): Flow<List<FavoriteArticle>>

}