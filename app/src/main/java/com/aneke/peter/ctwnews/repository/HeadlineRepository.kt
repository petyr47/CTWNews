package com.aneke.peter.ctwnews.repository

import com.aneke.peter.ctwnews.network.model.NewsResponse
import com.aneke.peter.ctwnews.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {
    suspend fun getHeadLines(key: String, source : String) : Flow<Resource<NewsResponse>>

}