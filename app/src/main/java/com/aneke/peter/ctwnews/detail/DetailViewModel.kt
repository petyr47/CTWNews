package com.aneke.peter.ctwnews.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aneke.peter.ctwnews.network.model.Article

class DetailViewModel : ViewModel() {

    val article = MutableLiveData<Article>()

}