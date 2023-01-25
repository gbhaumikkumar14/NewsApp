package com.example.newsapp.data.repository.dataSource

import com.example.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getSavedArticles(): Flow<List<Article>>
}