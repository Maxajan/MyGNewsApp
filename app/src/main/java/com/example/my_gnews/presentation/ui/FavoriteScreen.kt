package com.example.my_gnews.presentation.ui

import android.R.attr.contentDescription
import android.R.attr.maxLines
import com.example.my_gnews.R

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.my_gnews.data.local.entity.FavoriteArticle
import com.example.my_gnews.data.network.dto.Article
import com.example.my_gnews.presentation.viewModel.NewsViewModel

@Composable
fun FavoriteScreen(
    viewModel: NewsViewModel ,
    onArticleClick:(String)-> Unit,
    onBackClick:()-> Unit,
    onRemoveClick: (FavoriteArticle)-> Unit
) {
    val favoriteArticles by viewModel.favorites.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Back")
        }

        LazyColumn {
            items(favoriteArticles) { article ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                        .clickable { onArticleClick(article.url) }
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        val model = article.image.takeIf { it.isNotBlank() }
                        AsyncImage(
                            model = model,
                            contentDescription = "Favorite article image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(150.dp),
                           )
                        Spacer(modifier = Modifier.height(8.dp))

                     Text(
                         article.title, fontWeight = FontWeight.Bold
                     )
                      Text(
                          article.description ,
                          maxLines = 3, overflow = TextOverflow.Ellipsis
                      )
                        Text(
                            "Published at: ${article.publishedAt }",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Button(
                            onClick = {onRemoveClick(article)},
                            modifier = Modifier.fillMaxWidth() .padding(8.dp)
                            ) {
                            Text("Remove")
                        }
                    }
                }
            }
        }
    }
}