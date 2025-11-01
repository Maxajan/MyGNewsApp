package com.example.my_gnews
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.my_gnews.presentation.ui.FavoriteScreen
import com.example.my_gnews.presentation.navigation.FavoritesScreenRoute
import com.example.my_gnews.presentation.ui.NewsScreen
import com.example.my_gnews.presentation.navigation.NewsScreenRoute
import com.example.my_gnews.presentation.navigation.NewsUrlPage
import com.example.my_gnews.presentation.navigation.NewsUrlRoute
import com.example.my_gnews.presentation.navigation.SearchScreenRoute
import com.example.my_gnews.presentation.ui.SearchScreen
import com.example.my_gnews.presentation.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: NewsViewModel = hiltViewModel()

            NavHost(navController = navController, startDestination = NewsScreenRoute) {
                composable<NewsScreenRoute> {
                    NewsScreen(
                     viewModel = viewModel,
                        onArticleClick = {article ->
                          navController.navigate(NewsUrlRoute(article.url))
                        },
                        onFavoriteClick = {
                            navController.navigate(FavoritesScreenRoute)
                        },
                        onSearchClick = {
                            navController.navigate(SearchScreenRoute)
                        }
                    )
                }
                composable<SearchScreenRoute> {
                 //   Log.d("Navigation", "Navigated to SearchScreenRoute")
                    SearchScreen(
                        viewModel = viewModel,
                        onArticleClick = { article ->
                            navController.navigate(NewsUrlRoute(article.url))
                        },
                       onSaveClick = { article->
                        //   Log.d("Navigation", "Save clicked: ${article.title}")
                            viewModel.saveToFavoriteArticles(article)
                       },
                        onBackClick = {navController.popBackStack()}
                    )
                }
                composable<NewsUrlRoute> {
                    val args = it.toRoute<NewsUrlRoute>()
                    NewsUrlPage(
                        url = args.url,
                        onBackClick = {navController.popBackStack()}
                    )
                }

                composable<FavoritesScreenRoute> {
                    FavoriteScreen(
                     viewModel = viewModel,
                        onArticleClick = { url->
                            navController.navigate(NewsUrlRoute(url))
                        },
                        onBackClick = {navController.popBackStack()},
                        onRemoveClick = {article ->
                            viewModel.removeFavoriteArticle(article)
                        }
                    )
                }
            }

        }
    }
}

