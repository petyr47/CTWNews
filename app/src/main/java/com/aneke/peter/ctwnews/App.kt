package com.aneke.peter.ctwnews

import android.app.Application
import com.aneke.peter.ctwnews.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        insertKoin()
    }

    private fun insertKoin() {
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModules)
        }
    }

}