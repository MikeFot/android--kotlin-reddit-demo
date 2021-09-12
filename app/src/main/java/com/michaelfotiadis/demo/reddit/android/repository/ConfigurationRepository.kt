package com.michaelfotiadis.demo.reddit.android.repository

import android.content.res.Resources
import com.michaelfotiadis.demo.reddit.android.BuildConfig
import com.michaelfotiadis.demo.reddit.android.R

class ConfigurationRepository(private val resources: Resources) {

    val isDebugEnabled: Boolean
        get() = BuildConfig.DEBUG

    val isLandscape: Boolean
        get() = resources.getBoolean(R.bool.isLandscape)
    val isTablet: Boolean
        get() = resources.getBoolean(R.bool.isTablet)
    val is600dp: Boolean
        get() = resources.getBoolean(R.bool.is600)
    val is720dp: Boolean
        get() = resources.getBoolean(R.bool.is720)
    val is1080dp: Boolean
        get() = resources.getBoolean(R.bool.is1080)

}