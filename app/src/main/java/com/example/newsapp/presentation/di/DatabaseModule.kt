package com.example.newsapp.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.db.NewsDAO
import com.example.newsapp.data.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): NewsDatabase{
        return Room.databaseBuilder(
            app.applicationContext,
            NewsDatabase::class.java,
            "News_DB"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNewsDAO(newsDatabase: NewsDatabase): NewsDAO{
        return newsDatabase.newsDAO
    }
}