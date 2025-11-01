package com.example.my_gnews.di

import android.content.Context
import androidx.room.Room
import com.example.my_gnews.data.local.NewsDataBase
import com.example.my_gnews.data.local.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn (SingletonComponent::class)

object DataBaseModule {
    @Singleton
    @Provides
    fun provideDataBase (@ApplicationContext context: Context): NewsDataBase {
        return Room.databaseBuilder (
            context,
            NewsDataBase::class.java,
            "news_db",
        ).build()


    }
    @Singleton
    @Provides
    fun provideFavoriteDao(dataBase: NewsDataBase): FavoriteDao{
        return dataBase.favoriteDao()
    }
}