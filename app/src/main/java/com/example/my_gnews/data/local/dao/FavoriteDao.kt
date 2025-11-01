package com.example.my_gnews.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.my_gnews.data.local.entity.FavoriteArticle
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)  ////
suspend fun addToFavoriteArticles(article: FavoriteArticle)

@Delete
suspend fun removeFromFavoriteArticles(article: FavoriteArticle)

@Query ("SELECT * FROM favorite_article")
 fun getAllFavoriteArticles(): Flow<List<FavoriteArticle>>
}