package com.example.my_gnews.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.my_gnews.data.network.dto.Article
import com.example.my_gnews.presentation.viewModel.NewsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my_gnews.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onArticleClick:(Article)-> Unit,
    onSaveClick:(Article)->Unit,
    onBackClick: ()-> Unit
) {
    var query by remember { mutableStateOf("") }
    val searchResult by viewModel.searchNews.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Search News", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2)),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search News") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                singleLine = true,
            )
            Button(
                onClick = { viewModel.searchNews(query) },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text("Search")
            }
            when (searchResult) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is Resource.Success -> {
                    val articles = (searchResult as Resource.Success<List<Article>>).data
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items (articles){ article ->
                            ArticleCard(
                                article = article,
                                onClick = { onArticleClick(article) },
                                onSaveClick = {onSaveClick(article)}
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Text("Error loading search results", color = Color.Red)
                }
            }
        }

    }
}
