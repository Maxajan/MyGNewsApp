package com.example.my_gnews.presentation.viewModel
import android.R.attr.category
import android.R.attr.country
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_gnews.BuildConfig
import com.example.my_gnews.data.local.dao.FavoriteDao
import com.example.my_gnews.data.local.entity.FavoriteArticle
import com.example.my_gnews.data.mapper.toFavoriteEntity
import com.example.my_gnews.data.network.dto.Article
import com.example.my_gnews.domain.NewsRepository
import com.example.my_gnews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val favoriteDao: FavoriteDao
): ViewModel() {
    private val _topHeadLines = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)
    val topHeadLines: StateFlow<Resource<List<Article>>> = _topHeadLines

    private val _favorites = MutableStateFlow<List<FavoriteArticle>>(emptyList())
    val favorites: StateFlow<List<FavoriteArticle>> = _favorites

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _searchNews = MutableStateFlow<Resource<List<Article>>>(Resource.Success(emptyList()))//
    val searchNews: StateFlow<Resource<List<Article>>> = _searchNews


    init {

        fetchTopHeadLines("general", "us", "en")

        collectFavorites()
    }

    fun fetchTopHeadLines (
        category: String,
        country: String,
        language: String,)
    {
        viewModelScope.launch{
            _isRefreshing.value = true
            _topHeadLines.emit(Resource.Loading)
            try {
             repository.getTopHeadlines(
                 category=category,
                 country=country,
                 language=language,
                 apiKey = BuildConfig.GNEWS_API_KEY
             ).collect{result: Resource<List<Article>> ->
                 _topHeadLines.emit(result)
             }
            } catch (exception: Exception){
                _topHeadLines.emit(Resource.Error(exception))
            }
            _isRefreshing.value = false
        }
    }

    fun saveToFavoriteArticles(article: Article){
               viewModelScope.launch{
                   try {
                       Log.d("SaveDebug", "Trying to save: ${article.title}")
                       favoriteDao.addToFavoriteArticles(article.toFavoriteEntity())
                       Log.d("SaveDebug", "Saved successfully")
                   } catch (e: Exception){
                       Log.e("SaveDebug", "Error saving article", e)
                   }

        }
    }

    private fun collectFavorites (){
        viewModelScope.launch{
           repository.getAllFavoriteArticles().collect{ updatedList ->
               _favorites.value = updatedList
           }
        }
    }

    fun removeFavoriteArticle(article: FavoriteArticle){
      viewModelScope.launch{
          favoriteDao.removeFromFavoriteArticles(article)
      }
    }

    fun searchNews(query: String){
        viewModelScope.launch{
            _searchNews.value = Resource.Loading
       repository.getSearchNews(
           query=query,
           country = "us",
           language = "en",
           BuildConfig.GNEWS_API_KEY
       ).collect { result->
           _searchNews.value = result
       }
        }
    }

}