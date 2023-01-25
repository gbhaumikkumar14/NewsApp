package com.example.newsapp.data.repository

import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.dataSource.NewsLocalDataSource
import com.example.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.example.newsapp.data.util.Resource
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource
    ): NewsRepository
{
    override suspend fun getNewsHeadlines(
        country: String, page: Int): Resource<APIResponse> {
        return responseToResource(remoteDataSource
            .getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String, page: Int, aQuery: String): Resource<APIResponse> {
        return responseToResource(
            remoteDataSource.getSearchedTopHeadlines(country, page, aQuery))
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return localDataSource.getSavedArticles()
    }

    override suspend fun saveNews(article: Article) {
        localDataSource.saveArticle(article)
    }

    override suspend fun deleteNews(article: Article) {
        localDataSource.deleteArticle(article)
    }

    private fun responseToResource(
        response: Response<APIResponse>): Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let { result -> return Resource.Success(result) }
        }
        return Resource.Error(response.message())
    }
}