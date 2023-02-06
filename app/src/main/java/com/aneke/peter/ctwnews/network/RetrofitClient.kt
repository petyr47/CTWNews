package com.aneke.peter.ctwnews.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val baseUrl = "https://newsapi.org/v2/"

    fun makeApiService(): ApiInterface {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient(provideLoggingInterceptor()))
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
                .asLenient())
            .build().create(ApiInterface::class.java)
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val b = OkHttpClient.Builder()
        b.connectTimeout(60, TimeUnit.SECONDS)
        b.readTimeout(60, TimeUnit.SECONDS)
        b.writeTimeout(60, TimeUnit.SECONDS)
        b.addInterceptor(interceptor)
        return b.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}