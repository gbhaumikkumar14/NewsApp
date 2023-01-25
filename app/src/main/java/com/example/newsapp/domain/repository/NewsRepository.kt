package com.example.newsapp.domain.repository

import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(
        country: String, page: Int): Resource<APIResponse>

    suspend fun getSearchedNews(
        country: String, page: Int, aQuery: String): Resource<APIResponse>

    fun getSavedNews(): Flow<List<Article>>

    suspend fun saveNews(article: Article)

    suspend fun deleteNews(article: Article)

}