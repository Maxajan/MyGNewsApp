package com.example.my_gnews.data.repository

import com.example.my_gnews.data.local.dao.FavoriteDao
import com.example.my_gnews.data.local.entity.FavoriteArticle
import com.example.my_gnews.data.network.api.GNewsApi
import com.example.my_gnews.data.network.dto.Article
import com.example.my_gnews.domain.NewsRepository
import com.example.my_gnews.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: GNewsApi,
    private val favoriteDao: FavoriteDao

) : NewsRepository {
    override fun getTopHeadlines(
        category: String,
        country: String,
        language: String,
        apikey: String
    ): Flow<Resource<List<Article>>> = flow {
    emit(Resource.Loading)
        try {
            val response = api.getTopHeadlines(
                category = category,
                country = country,
                language = language,
                apiKey = apikey)

            emit(Resource.Success(response.articles))
        } catch (exception: Exception){
            emit(Resource.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override fun getSearchNews(
        query: String,
        country: String,
        language: String,
        apikey: String
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = api.searchNews(
                    query = query,
                    country = country,
                    language = language,
                    apiKey = apikey)

                emit(Resource.Success(response.articles))
            } catch (exception: Exception) {
                emit(Resource.Error(exception))
            }
        }
    }

    override fun getAllFavoriteArticles(): Flow<List<FavoriteArticle>> {
     return favoriteDao.getAllFavoriteArticles()
    }
}