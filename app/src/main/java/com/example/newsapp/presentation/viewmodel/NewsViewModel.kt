package com.example.newsapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.APIResponse
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.util.Resource
import com.example.newsapp.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase
): AndroidViewModel(app) {
    val newsHeadlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadlines(country: String, page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadlines.postValue(Resource.Loading())
            try{
                if(isInternetAvailable(app)) {
                    val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                    newsHeadlines.postValue(apiResult)
                }else{
                    newsHeadlines.postValue(Resource.Error("Internet is not available"))
                }
            } catch (e:Exception){
                newsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getSearchedNewsHeadlines(country: String, page: Int, aQuery: String){
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadlines.postValue(Resource.Loading())
            try{
                if(isInternetAvailable(app)){
                    val apiResult = getSearchedNewsUseCase.execute(country, page, aQuery)
                    newsHeadlines.postValue(apiResult)
                }else{
                    newsHeadlines.postValue(Resource.Error("Internet is not available"))
                }
            }catch (e: Exception){
                newsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    private fun isInternetAvailable(context: Context):Boolean{
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    fun saveNewsToDatabase(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            saveNewsUseCase.execute(article)
        }
    }

    fun deleteNewsFromDatabase(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            deleteSavedNewsUseCase.execute(article)
        }
    }

    fun getSavedNews() =
        liveData {
            getSavedNewsUseCase.execute().collect{
                emit(it)
            }
        }

}