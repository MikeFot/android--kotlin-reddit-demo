package com.michaelfotiadis.demo.reddit.android.data.network.model


import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("kind")
    val kind: String
)