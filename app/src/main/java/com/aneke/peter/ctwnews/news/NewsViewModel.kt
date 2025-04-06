package com.aneke.peter.ctwnews.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aneke.peter.ctwnews.network.model.NewsResponse
import com.aneke.peter.ctwnews.repository.HeadlineRepositoryImpl
import com.aneke.peter.ctwnews.utils.Resource
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: HeadlineRepositoryImpl) : ViewModel() {

    val headlineResponse = MutableLiveData<Resource<NewsResponse>>()

    fun fetchHeadlines(key : String, source : String) {
        viewModelScope.launch {
            repository.getHeadLines(key, source).collect{
                headlineResponse.postValue(it)
            }
        }
    }

}