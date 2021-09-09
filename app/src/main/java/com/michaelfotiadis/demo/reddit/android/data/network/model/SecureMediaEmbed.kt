package com.michaelfotiadis.demo.reddit.android.data.network.model


import com.google.gson.annotations.SerializedName

data class SecureMediaEmbed(
    @SerializedName("content")
    val content: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("media_domain_url")
    val mediaDomainUrl: String,
    @SerializedName("scrolling")
    val scrolling: Boolean,
    @SerializedName("width")
    val width: Int
)