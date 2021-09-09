package com.michaelfotiadis.demo.reddit.android.data.network

import com.michaelfotiadis.demo.reddit.android.data.network.model.RedditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {

    @GET("/r/Android/hot.json")
    suspend fun getRedditPosts(
        @Query("after") after: String? = null
    ): Response<RedditResponse>

}