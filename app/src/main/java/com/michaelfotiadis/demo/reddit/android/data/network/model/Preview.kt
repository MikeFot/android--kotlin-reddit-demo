package com.michaelfotiadis.demo.reddit.android.data.network.model


import com.google.gson.annotations.SerializedName

data class Preview(
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("images")
    val images: List<Image>
)