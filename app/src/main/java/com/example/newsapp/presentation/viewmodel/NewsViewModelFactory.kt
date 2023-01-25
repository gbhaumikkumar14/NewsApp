package com.example.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.domain.usecase.*

class NewsViewModelFactory(private val app: Application,
                           private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
                           private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
                           private val saveNewsUseCase: SaveNewsUseCase,
                           private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
                           private val getSavedNewsUseCase: GetSavedNewsUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            getNewsHeadlinesUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            deleteSavedNewsUseCase,
            getSavedNewsUseCase
        ) as T
    }
}