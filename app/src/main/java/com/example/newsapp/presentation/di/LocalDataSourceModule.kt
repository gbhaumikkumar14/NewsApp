package com.example.newsapp.presentation.di

import com.example.newsapp.data.db.NewsDAO
import com.example.newsapp.data.repository.dataSource.NewsLocalDataSource
import com.example.newsapp.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {
    @Singleton
    @Provides
    fun provideNewsLocalDataSource(newsDAO: NewsDAO): NewsLocalDataSource{
        return NewsLocalDataSourceImpl(newsDAO)
    }
}