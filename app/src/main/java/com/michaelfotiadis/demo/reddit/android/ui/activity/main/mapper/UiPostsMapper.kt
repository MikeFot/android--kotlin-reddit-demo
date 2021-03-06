package com.michaelfotiadis.demo.reddit.android.ui.activity.main.mapper

import com.michaelfotiadis.demo.reddit.android.data.db.Post
import com.michaelfotiadis.demo.reddit.android.domain.TimeAgoMessageInteractor
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.UiPost

class UiPostsMapper(private val timeAgoMessageInteractor: TimeAgoMessageInteractor) {

    fun map(dbPost: Post) = UiPost(
        id = dbPost.id,
        title = dbPost.title,
        previewContent = dbPost.selfText,
        permalink = "https://www.reddit.com${dbPost.permalink}",
        timeAgoMessage = timeAgoMessageInteractor.getFormattedText(dbPost.createdUtc),
        thumbnailLink = dbPost.thumbnailLink
    )

}