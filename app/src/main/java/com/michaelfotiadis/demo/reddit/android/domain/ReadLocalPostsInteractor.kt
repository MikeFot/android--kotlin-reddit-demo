package com.michaelfotiadis.demo.reddit.android.domain

import androidx.paging.DataSource
import com.michaelfotiadis.demo.reddit.android.data.db.Post
import com.michaelfotiadis.demo.reddit.android.data.db.PostsDao

class ReadLocalPostsInteractor(
    private val postsDao: PostsDao
) {

    fun readPosts(): DataSource.Factory<Int, Post> {
        return postsDao.getAllPaged()
    }

}