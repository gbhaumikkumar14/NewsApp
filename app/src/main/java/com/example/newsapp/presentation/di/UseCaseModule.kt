package com.example.newsapp.presentation.di

import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCase(repository: NewsRepository): GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSearchedNewsUseCase(repository: NewsRepository): GetSearchedNewsUseCase{
        return GetSearchedNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(repository: NewsRepository): SaveNewsUseCase{
        return SaveNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedNewsUseCase(repository: NewsRepository): DeleteSavedNewsUseCase{
        return DeleteSavedNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(repository: NewsRepository): GetSavedNewsUseCase{
        return GetSavedNewsUseCase(repository)
    }
}