package com.aneke.peter.ctwnews.repository

import com.aneke.peter.ctwnews.network.model.NewsResponse

interface HeadlineRepository {
    suspend fun getHeadLines(key: String, source : String) : NewsResponse

}