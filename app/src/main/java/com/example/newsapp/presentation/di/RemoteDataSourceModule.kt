package com.example.newsapp.presentation.di

import com.example.newsapp.data.api.NewsService
import com.example.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.example.newsapp.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {
    @Singleton
    @Provides
    fun provideRemoteDataSource(newsService: NewsService): NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsService)
    }
}