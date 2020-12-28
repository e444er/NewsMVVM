package com.devv.newsmvvm.ui

import androidx.lifecycle.ViewModel
import com.devv.newsmvvm.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel(){
}