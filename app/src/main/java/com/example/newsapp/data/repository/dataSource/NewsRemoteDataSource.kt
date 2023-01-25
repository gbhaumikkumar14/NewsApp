package com.example.newsapp.data.repository.dataSource

import com.example.newsapp.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int)
        : Response<APIResponse>
    suspend fun getSearchedTopHeadlines(
        country: String, page: Int, query: String): Response<APIResponse>
}