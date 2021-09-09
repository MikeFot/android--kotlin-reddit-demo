package com.michaelfotiadis.demo.reddit.android.di

import com.google.gson.GsonBuilder
import com.michaelfotiadis.demo.reddit.android.ApplicationInitializer
import com.michaelfotiadis.demo.reddit.android.repository.PostsRepository
import com.michaelfotiadis.demo.reddit.android.network.factory.OkHttpBuilderFactory
import com.michaelfotiadis.demo.reddit.android.network.factory.RetrofitBuilderFactory
import com.michaelfotiadis.demo.reddit.android.repository.BuildConfigRepository
import com.michaelfotiadis.demo.reddit.android.repository.error.mapper.RetrofitErrorMapper
import com.michaelfotiadis.demo.reddit.android.ui.activity.MainActivity
import com.michaelfotiadis.demo.reddit.android.ui.error.UiErrorMapper
import okhttp3.Cache
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        BuildConfigRepository()
    }

    single {
        ApplicationInitializer(get())
    }

    single {
        GsonBuilder().setPrettyPrinting().create()
    }
}

val networkModule = module {

    single {
        Cache(androidContext().cacheDir, 10485760L)
    }

    single {
        OkHttpBuilderFactory.provideOkHttpClientBuilder(
            cache = get(),
            isDebugEnabled = get<BuildConfigRepository>().isDebugEnabled
        )
            .build()
    }

    factory {
        RetrofitBuilderFactory.provideRetrofit(
            baseUrl = "https://www.reddit.com",
            gson = get(),
            okHttpClient = get()
        ).build()
    }

}

val repositoryModule = module {

    factory {
        RetrofitErrorMapper()
    }

    factory {
        PostsRepository(
            retrofit = get(),
            retrofitErrorMapper = get()
        )
    }

}

val mainActivityModule = module {
    scope<MainActivity> {
        scoped {
            UiErrorMapper()
        }
    }
}