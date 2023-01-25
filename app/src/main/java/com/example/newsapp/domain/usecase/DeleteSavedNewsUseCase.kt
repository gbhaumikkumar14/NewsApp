package com.example.newsapp.domain.usecase

import com.example.newsapp.data.model.Article
import com.example.newsapp.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val repo: NewsRepository) {
    suspend fun execute(aArticle: Article) = repo.deleteNews(aArticle)
}