package com.michaelfotiadis.demo.reddit.android.ui.activity.main.mapper

import com.michaelfotiadis.demo.reddit.android.data.db.Post
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.UiPost

class UiPostsMapper {

    fun map(dbPost: Post) = UiPost(
        id = dbPost.id,
        title = dbPost.title,
        previewContent = dbPost.selfText,
        fullContent = dbPost.selfText,
        permalink = dbPost.permalink
    )

}