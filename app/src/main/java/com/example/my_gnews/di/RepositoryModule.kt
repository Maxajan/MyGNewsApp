package com.example.my_gnews.di

import com.example.my_gnews.data.repository.NewsRepositoryImpl
import com.example.my_gnews.domain.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton

     abstract fun bindRepository (
        implRepository: NewsRepositoryImpl
    ): NewsRepository
}