package com.michaelfotiadis.demo.reddit.android

import android.annotation.SuppressLint
import android.util.Log
import com.michaelfotiadis.demo.reddit.android.repository.ConfigurationRepository
import timber.log.Timber

class ApplicationInitializer(
    private val configurationRepository: ConfigurationRepository
) {

    @SuppressLint("LogNotTimber")
    fun initialiseLogging() {
        if (configurationRepository.isDebugEnabled) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber initialised.")
        } else {
            Log.d(this::class.simpleName, "Skipped Timber initialisation")
        }
    }

}