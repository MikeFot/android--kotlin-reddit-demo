package com.michaelfotiadis.demo.reddit.android.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.michaelfotiadis.demo.reddit.android.ApplicationInitializer
import com.michaelfotiadis.demo.reddit.android.data.db.AppDatabase
import com.michaelfotiadis.demo.reddit.android.domain.GetColumnCountInteractor
import com.michaelfotiadis.demo.reddit.android.domain.GetPostsInteractor
import com.michaelfotiadis.demo.reddit.android.repository.PostsRepository
import com.michaelfotiadis.demo.reddit.android.network.factory.OkHttpBuilderFactory
import com.michaelfotiadis.demo.reddit.android.network.factory.RetrofitBuilderFactory
import com.michaelfotiadis.demo.reddit.android.repository.ConfigurationRepository
import com.michaelfotiadis.demo.reddit.android.repository.error.mapper.RetrofitErrorMapper
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.MainActivity
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.MainViewModel
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.mapper.UiPostsMapper
import com.michaelfotiadis.demo.reddit.android.ui.error.UiErrorMapper
import okhttp3.Cache
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        ConfigurationRepository(androidContext().resources)
    }

    single {
        ApplicationInitializer(configurationRepository = get())
    }

    single {
        GsonBuilder().setPrettyPrinting().create()
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            AppDatabase::class.java,
            "reddit-posts-db"
        ).build()
    }
}

val networkModule = module {

    single {
        Cache(androidContext().cacheDir, 10485760L)
    }

    single {
        OkHttpBuilderFactory.provideOkHttpClientBuilder(
            cache = get(),
            isDebugEnabled = get<ConfigurationRepository>().isDebugEnabled
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

val domainModule = module {

    factory {
        GetPostsInteractor(postsRepository = get())
    }

    factory {
        GetColumnCountInteractor(configurationRepository = get())
    }
}

val mainActivityModule = module {
    scope<MainActivity> {
        scoped {
            UiErrorMapper()
        }
        scoped {
            UiPostsMapper()
        }
        viewModel {
            MainViewModel(
                getPostsInteractor = get(),
                getColumnsInteractor = get(),
                uiPostsMapper = get(),
                uiErrorMapper = get()
            )
        }
    }
}