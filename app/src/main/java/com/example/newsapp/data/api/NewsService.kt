package com.example.newsapp.data.api

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET(value = "/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query(value = "country")
        country: String,
        @Query(value = "page")
        page: Int,
        @Query(value = "apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>

    @GET(value = "/v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query(value = "country")
        country: String,
        @Query(value = "page")
        page: Int,
        @Query(value = "q")
        query: String,
        @Query(value = "apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>
}