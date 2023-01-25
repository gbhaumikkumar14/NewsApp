package com.example.newsapp.domain.usecase

import com.example.newsapp.data.model.Article
import com.example.newsapp.domain.repository.NewsRepository

class SaveNewsUseCase(private val repo: NewsRepository) {
    suspend fun execute(aArticle: Article) = repo.saveNews(aArticle)
}