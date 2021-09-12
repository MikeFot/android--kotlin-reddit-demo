package com.michaelfotiadis.demo.reddit.android.repository

import com.michaelfotiadis.demo.reddit.android.data.network.PostsApi
import com.michaelfotiadis.demo.reddit.android.data.network.model.RedditResponse
import com.michaelfotiadis.demo.reddit.android.network.error.RetrofitException
import com.michaelfotiadis.demo.reddit.android.repository.error.mapper.RetrofitErrorMapper
import com.michaelfotiadis.demo.reddit.android.repository.result.RepoResult
import retrofit2.Retrofit

class PostsRepository(
    private val retrofit: Retrofit,
    private val retrofitErrorMapper: RetrofitErrorMapper
) {

    private val api: PostsApi = retrofit.create(PostsApi::class.java)

    suspend fun getRedditPosts(after: String?): RepoResult<RedditResponse> {

        return try {
            val response = api.getRedditPosts(after)
            if (response.isSuccessful) {
                RepoResult.SuccessResult(response.body() as RedditResponse)
            } else {
                val retrofitException = RetrofitException.httpError(
                    "",
                    response,
                    retrofit
                )
                RepoResult.ErrorResult(retrofitErrorMapper.convert(retrofitException))
            }
        } catch (exception: Exception) {
            RepoResult.ErrorResult(retrofitErrorMapper.convert(exception))
        }
    }

}