package com.michaelfotiadis.demo.reddit.android.domain

import com.michaelfotiadis.demo.reddit.android.data.network.model.RedditResponse
import com.michaelfotiadis.demo.reddit.android.repository.PostsLocalRepository
import com.michaelfotiadis.demo.reddit.android.repository.PostsNetworkRepository
import com.michaelfotiadis.demo.reddit.android.repository.mapper.RedditPostsLocalMapper
import com.michaelfotiadis.demo.reddit.android.repository.result.RepoResult

class FetchAndWritePostsInteractor(
    private val postsNetworkRepository: PostsNetworkRepository,
    private val postsLocalRepository: PostsLocalRepository,
    private val localMapper: RedditPostsLocalMapper
) {

    suspend fun getPosts(after: String?, isRefresh: Boolean): RepoResult<RedditResponse> {

        return when (val result = postsNetworkRepository.getRedditPosts(after)) {
            is RepoResult.ErrorResult -> {
                result
            }
            is RepoResult.SuccessResult -> {
                if (isRefresh) {
                    postsLocalRepository.nukeTable()
                }
                val entities = localMapper.map(result.payload.data.children)
                postsLocalRepository.insertPosts(entities)
                result
            }
        }
    }

}