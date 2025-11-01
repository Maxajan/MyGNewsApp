package com.example.my_gnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.my_gnews.data.local.dao.FavoriteDao
import com.example.my_gnews.data.local.entity.FavoriteArticle

@Database (entities = [FavoriteArticle::class], version = 1)
abstract class NewsDataBase: RoomDatabase (){
    abstract fun favoriteDao(): FavoriteDao
}