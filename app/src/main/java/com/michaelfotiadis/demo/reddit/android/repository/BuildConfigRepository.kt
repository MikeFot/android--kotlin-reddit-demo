package com.michaelfotiadis.demo.reddit.android.repository

import com.michaelfotiadis.demo.reddit.android.BuildConfig

class BuildConfigRepository {

    val isDebugEnabled: Boolean
        get() = BuildConfig.DEBUG

}