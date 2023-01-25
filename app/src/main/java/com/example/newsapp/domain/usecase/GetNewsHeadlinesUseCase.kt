package com.example.newsapp.domain.usecase

import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.util.Resource
import com.example.newsapp.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val repo : NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<APIResponse>{
        return repo.getNewsHeadlines(country, page)
    }
}