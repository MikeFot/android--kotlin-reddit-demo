package com.michaelfotiadis.demo.reddit.android.domain

import com.github.marlonlom.utilities.timeago.TimeAgo
import com.github.marlonlom.utilities.timeago.TimeAgoMessages
import java.util.*


class TimeAgoMessageInteractor(locale: Locale) {

    private val messages: TimeAgoMessages = TimeAgoMessages.Builder()
        .withLocale(Locale.forLanguageTag(locale.toLanguageTag()))
        .build()

    fun getFormattedText(timeInSeconds: Long): String {
        return TimeAgo.using(timeInSeconds * 1000, messages)
    }

}