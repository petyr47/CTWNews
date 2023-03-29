package com.aneke.peter.ctwnews.repository

import com.aneke.peter.ctwnews.network.ApiInterface
import com.aneke.peter.ctwnews.network.model.NewsResponse
import com.aneke.peter.ctwnews.utils.resolveMessage

class HeadlineRepositoryImpl(private val service : ApiInterface) : HeadlineRepository {

    override suspend fun getHeadLines(key : String, source : String) : NewsResponse {
        return try {
            service.getHeadlines(source, key)
        } catch (e: Exception) {
            e.printStackTrace()
            NewsResponse(success = false, message = e.resolveMessage())
        }
    }

}