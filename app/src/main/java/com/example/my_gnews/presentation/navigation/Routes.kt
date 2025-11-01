package com.example.my_gnews.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object NewsScreenRoute

@Serializable
data class NewsUrlRoute(
    val url: String
)

@Serializable
object FavoritesScreenRoute

@Serializable
data object SearchScreenRoute