package com.michaelfotiadis.demo.reddit.android.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME_POSTS = "posts"

@Entity(tableName = TABLE_NAME_POSTS)
data class Post(
    @PrimaryKey
    val id: String,
    val title: String,
    val authorName: String,
    val selfText: String,
    val selfTextHtml: String? = null,
    val permalink: String,
    val upVotes: Int = 0,
    val downVotes: Int = 0,
    val thumbnailLink: String? = null,
    val containsSpoilers: Boolean = false,
    val countOfAwards: Int = 0
)