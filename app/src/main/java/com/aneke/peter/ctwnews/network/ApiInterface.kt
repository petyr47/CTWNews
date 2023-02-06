package com.aneke.peter.ctwnews.network

import com.aneke.peter.ctwnews.network.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    suspend fun getHeadlines(@Query("sources")source: String, @Query("apiKey")apiKey : String) : NewsResponse

}