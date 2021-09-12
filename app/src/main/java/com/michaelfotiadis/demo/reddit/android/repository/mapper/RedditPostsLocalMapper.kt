package com.michaelfotiadis.demo.reddit.android.repository.mapper

import com.michaelfotiadis.demo.reddit.android.data.db.Post
import com.michaelfotiadis.demo.reddit.android.data.network.model.Children

class RedditPostsLocalMapper {

    fun map(
        children: List<Children>
    ): List<Post> {

        val dbPosts = mutableListOf<Post>()

        children.forEach { child ->
            child.data.let { item ->
                Post(
                    id = item.id,
                    title = item.title,
                    authorName = item.author,
                    selfText = when {
                        item.selftext.isBlank() -> item.url
                        else -> item.selftext
                    },
                    selfTextHtml = item.selftextHtml,
                    permalink = item.permalink,
                    upVotes = item.ups,
                    downVotes = item.downs,
                    thumbnailLink = item.thumbnail,
                    containsSpoilers = item.spoiler,
                    countOfAwards = item.allAwardings.size
                ).apply(dbPosts::add)
            }
        }

        return dbPosts
    }

}