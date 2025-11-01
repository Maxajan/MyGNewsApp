package com.example.my_gnews.presentation.ui
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.my_gnews.R
import com.example.my_gnews.data.network.dto.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenForUi(
    articles:List<Article>,
    isRefreshing: Boolean,
    onArticleClick: (Article)-> Unit,
    onFavoriteClick:()-> Unit,
    onRefresh:()-> Unit,
    onSaveClick:(Article)-> Unit,
    onSearchClick:()-> Unit

    ) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My News App", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2)),
                actions = {
                    IconButton(  onClick = onSearchClick,){
                        Icon (Icons.Default.Search, contentDescription = "Search News")                  }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            Button(
                onClick = onFavoriteClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text("Saved Favorites")
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = onRefresh,
                enabled = !isRefreshing,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                if (isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (isRefreshing) "Refreshing...." else "Refresh News")
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(articles) { oneArticle ->
                    ArticleCard(
                        article = oneArticle,
                        onClick = { onArticleClick(oneArticle) },
                        onSaveClick = { onSaveClick(oneArticle) }
                    )
                }
            }
        }
    }
}

    @Composable
    fun ArticleCard(
        article: Article,
        onClick: () -> Unit,
        onSaveClick: () -> Unit
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(4.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    placeholder = painterResource(id = R.drawable.clock),
                    error = painterResource(id = R.drawable.nature)
                )

                Text(
                    text = article.title ?: "No Title available",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Button(
                    onClick = { onSaveClick()
                        Log.d("ArticleCard", "Save clicked for article: $article")
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)
                        .height(34.dp)
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }





