package com.michaelfotiadis.demo.reddit.android.domain

import com.michaelfotiadis.demo.reddit.android.data.network.model.RedditResponse
import com.michaelfotiadis.demo.reddit.android.repository.PostsRepository
import com.michaelfotiadis.demo.reddit.android.repository.result.RepoResult

class GetPostsInteractor(
    private val postsRepository: PostsRepository
) {

    suspend fun getPosts(after: String?): RepoResult<RedditResponse> {
        return postsRepository.getRedditPosts(after)
    }

}