package com.aneke.peter.ctwnews.di

import com.aneke.peter.ctwnews.detail.DetailViewModel
import com.aneke.peter.ctwnews.network.RetrofitClient
import com.aneke.peter.ctwnews.news.NewsViewModel
import com.aneke.peter.ctwnews.repository.HeadlineRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiInterface = RetrofitClient.makeApiService()

val dataModule = module {
    single { apiInterface }
}

val viewModels = module {
    viewModel { NewsViewModel(get()) }
    viewModel { DetailViewModel() }
}

val repositoryModule = module {
    single { HeadlineRepositoryImpl(get()) }
}

val appModules = listOf(dataModule, viewModels, repositoryModule)






