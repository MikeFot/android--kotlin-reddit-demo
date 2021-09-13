package com.michaelfotiadis.demo.reddit.android.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.michaelfotiadis.demo.reddit.android.ApplicationInitializer
import com.michaelfotiadis.demo.reddit.android.data.db.AppDatabase
import com.michaelfotiadis.demo.reddit.android.domain.CalculateColumnCountInteractor
import com.michaelfotiadis.demo.reddit.android.domain.FetchAndWritePostsInteractor
import com.michaelfotiadis.demo.reddit.android.repository.PostsNetworkRepository
import com.michaelfotiadis.demo.reddit.android.network.factory.OkHttpBuilderFactory
import com.michaelfotiadis.demo.reddit.android.network.factory.RetrofitBuilderFactory
import com.michaelfotiadis.demo.reddit.android.repository.ConfigurationRepository
import com.michaelfotiadis.demo.reddit.android.repository.PostsLocalRepository
import com.michaelfotiadis.demo.reddit.android.domain.ReadLocalPostsInteractor
import com.michaelfotiadis.demo.reddit.android.domain.TimeAgoMessageInteractor
import com.michaelfotiadis.demo.reddit.android.repository.error.mapper.RetrofitErrorMapper
import com.michaelfotiadis.demo.reddit.android.repository.mapper.RedditPostsLocalMapper
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.MainActivity
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.MainViewModel
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.mapper.UiPostsMapper
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.error.UiErrorMapper
import io.noties.markwon.Markwon
import okhttp3.Cache
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val appModule = module {

    single {
        ConfigurationRepository(androidContext().resources)
    }

    single {
        ApplicationInitializer(
            application = androidApplication(),
            configurationRepository = get()
        )
    }

    single {
        GsonBuilder().setPrettyPrinting().create()
    }

    single {
        Markwon.create(androidApplication())
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

    single {
        get<AppDatabase>().postsDao()
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
        PostsNetworkRepository(
            retrofit = get(),
            retrofitErrorMapper = get()
        )
    }

    factory {
        PostsLocalRepository(
            postsDao = get()
        )
    }

}

val domainModule = module {

    factory {
        RedditPostsLocalMapper()
    }

    factory {
        TimeAgoMessageInteractor(Locale.getDefault())
    }

    factory {
        FetchAndWritePostsInteractor(
            postsNetworkRepository = get(),
            postsLocalRepository = get(),
            localMapper = get()
        )
    }

    factory {
        CalculateColumnCountInteractor(configurationRepository = get())
    }

    factory {
        ReadLocalPostsInteractor(postsDao = get())
    }

}

val mainActivityModule = module {
    scope<MainActivity> {
        scoped {
            UiErrorMapper()
        }
        scoped {
            UiPostsMapper(
                timeAgoMessageInteractor = get()
            )
        }
        viewModel {
            MainViewModel(
                fetchAndWritePostsInteractor = get(),
                calculateColumnsInteractor = get(),
                readLocalPostsInteractor = get(),
                uiPostsMapper = get(),
                uiErrorMapper = get()
            )
        }
    }
}