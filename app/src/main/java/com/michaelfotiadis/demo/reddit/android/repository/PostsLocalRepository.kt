package com.michaelfotiadis.demo.reddit.android.repository

import com.michaelfotiadis.demo.reddit.android.data.db.Post
import com.michaelfotiadis.demo.reddit.android.data.db.PostsDao

class PostsLocalRepository(
    private val postsDao: PostsDao
) {

    suspend fun insertPosts(redditPosts: List<Post>) {
        postsDao.insertAll(redditPosts)
    }

    suspend fun deleteAllAndInsertPosts(redditPosts: List<Post>) {
        postsDao.deleteAndInsertEntries(redditPosts)
    }

    suspend fun nukeTable() {
        postsDao.nukeTable()
    }

}