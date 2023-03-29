package com.aneke.peter.ctwnews.repository

import com.aneke.peter.ctwnews.network.ApiInterface
import com.aneke.peter.ctwnews.network.model.NewsResponse
import com.aneke.peter.ctwnews.utils.Resource
import com.aneke.peter.ctwnews.utils.resolveMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HeadlineRepositoryImpl(private val service : ApiInterface) : HeadlineRepository {

    override suspend fun getHeadLines(key : String, source : String) : Flow<Resource<NewsResponse>>  = flow{
        emit(Resource.loading())
        try {
            val response = service.getHeadlines(source, key)
            emit(Resource.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(e.resolveMessage()))
        }
    }

}