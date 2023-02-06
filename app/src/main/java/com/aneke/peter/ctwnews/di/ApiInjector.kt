package com.aneke.peter.ctwnews.di

import com.aneke.peter.ctwnews.network.RetrofitClient
import com.aneke.peter.ctwnews.repository.HeadlineRepository
import org.koin.dsl.module

val apiInterface = RetrofitClient.makeApiService()

val dataModule = module {
    single { apiInterface }
}

val viewModels = module {

}

val repositoryModule = module {
    single { HeadlineRepository(get(), get()) }
}

val appModules = listOf(dataModule, viewModels, repositoryModule)






