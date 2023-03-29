package com.aneke.peter.ctwnews.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aneke.peter.ctwnews.network.model.NewsResponse
import com.aneke.peter.ctwnews.repository.HeadlineRepositoryImpl
import com.aneke.peter.ctwnews.utils.Resource
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: HeadlineRepositoryImpl) : ViewModel() {

    private val _headlineResponse = MutableLiveData<Resource<NewsResponse>>()
    val headlineResponse : LiveData<Resource<NewsResponse>>
        get() = _headlineResponse


    fun fetchHeadlines(key : String, source : String) {
        _headlineResponse.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.getHeadLines(key, source)
            if (result.success) {
                _headlineResponse.postValue(Resource.success(result, result.message))
            } else {
                _headlineResponse.postValue(Resource.error(result.message))
            }
        }
    }

}