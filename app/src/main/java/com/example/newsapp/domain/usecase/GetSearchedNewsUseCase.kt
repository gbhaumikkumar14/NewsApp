package com.example.newsapp.domain.usecase

import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.util.Resource
import com.example.newsapp.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val repo: NewsRepository) {
    suspend fun execute(country: String, page: Int, aQuery: String): Resource<APIResponse>{
        return repo.getSearchedNews(country, page, aQuery)
    }
}