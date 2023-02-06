package com.aneke.peter.ctwnews.repository

import android.content.Context
import com.aneke.peter.ctwnews.BuildConfig
import com.aneke.peter.ctwnews.R
import com.aneke.peter.ctwnews.network.ApiInterface
import com.aneke.peter.ctwnews.network.model.NewsResponse
import com.aneke.peter.ctwnews.utils.resolveMessage

class HeadlineRepository(private val context : Context, private val service : ApiInterface) {

    suspend fun getHeadLines() : NewsResponse {
        try {
            val key = context.getString(R.string.news_api_key)
            val source = BuildConfig.SOURCE
            return service.getHeadlines(source, key)
        } catch (e: Exception) {
            e.printStackTrace()
            return NewsResponse(success = false, message = e.resolveMessage())
        }
    }

}