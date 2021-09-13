package com.michaelfotiadis.demo.reddit.android.ui.activity.main.model

data class UiPost(
    val id: String,
    val title: String,
    val previewContent: String,
    val permalink: String,
    val timeAgoMessage: String,
    val thumbnailLink: String? = null
)
