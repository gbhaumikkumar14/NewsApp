package com.example.newsapp.data.repository.dataSourceImpl

import com.example.newsapp.data.db.NewsDAO
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val newsDAO: NewsDAO)
    : NewsLocalDataSource {
    override suspend fun saveArticle(article: Article) {
        newsDAO.insertArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDAO.deleteArticle(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return newsDAO.getSavedArticlesFromDb()
    }
}