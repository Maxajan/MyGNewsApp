package com.example.my_gnews.presentation.ui
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.my_gnews.data.network.dto.Article
import com.example.my_gnews.presentation.viewModel.NewsViewModel
import com.example.my_gnews.util.Resource

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onArticleClick: (Article)-> Unit,
    onFavoriteClick: ()-> Unit,
    onSearchClick: ()-> Unit

){
val result: Resource<List<Article>> by viewModel.topHeadLines.collectAsState()
val isRefreshing by viewModel.isRefreshing.collectAsState()

    when (result) {
        is Resource.Success ->{
            val articles = (result as Resource.Success<List<Article>>).data

            ScreenForUi(
                articles = articles,
                isRefreshing = isRefreshing,
                onArticleClick = onArticleClick,
                onFavoriteClick = onFavoriteClick,
                onRefresh = {
                    viewModel.fetchTopHeadLines("general", "us", "en")
                },
                onSaveClick = { article->
                    viewModel.saveToFavoriteArticles(article)

                },
                onSearchClick = onSearchClick
            )
        }
         is Resource.Error -> {
             val error = (result as Resource.Error).exception.message ?: "Unknown error"
             Column (
                 modifier = Modifier.fillMaxSize() .padding(16.dp),
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ){
                 Text("Error: $error", color = Color.Red)
                 Spacer(modifier = Modifier.height(8.dp))
                 Button(onClick = {
                   viewModel.fetchTopHeadLines("general", "us", "en")
                 }) {
                     Text("Retry")
                 }
             }
         }
        is Resource.Loading -> {
            ScreenForUi(
                articles = emptyList(),
                isRefreshing = true,
                onRefresh = {

                    viewModel.fetchTopHeadLines("general", "us", "en",)
                },
                onArticleClick = onArticleClick,
                onFavoriteClick = onFavoriteClick,
                onSaveClick = { article -> viewModel.saveToFavoriteArticles(article) },
                onSearchClick = onSearchClick
            )
        }

    }
}