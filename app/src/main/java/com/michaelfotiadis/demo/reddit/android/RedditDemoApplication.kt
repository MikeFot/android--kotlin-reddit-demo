package com.michaelfotiadis.demo.reddit.android

import android.app.Application
import com.michaelfotiadis.demo.reddit.android.di.appModule
import com.michaelfotiadis.demo.reddit.android.di.mainActivityModule
import com.michaelfotiadis.demo.reddit.android.di.networkModule
import com.michaelfotiadis.demo.reddit.android.di.repositoryModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RedditDemoApplication : Application() {

    private val applicationInitializer: ApplicationInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@RedditDemoApplication)
            modules(
                appModule,
                networkModule,
                repositoryModule,
                mainActivityModule
            )
        }

        applicationInitializer.initialiseLogging()
    }

}