package com.example.newsapp.data.repository.dataSourceImpl

import com.example.newsapp.data.api.NewsService
import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsService: NewsService
): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(
        country: String, page: Int): Response<APIResponse> {
        return newsService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedTopHeadlines(
        country: String,
        page: Int,
        query: String
    ): Response<APIResponse> {
        return newsService.getSearchedTopHeadlines(country, page, query)
    }
}