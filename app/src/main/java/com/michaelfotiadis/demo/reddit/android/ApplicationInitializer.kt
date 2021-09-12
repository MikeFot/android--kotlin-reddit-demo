package com.michaelfotiadis.demo.reddit.android

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.michaelfotiadis.demo.reddit.android.repository.ConfigurationRepository
import timber.log.Timber

@SuppressLint("LogNotTimber")
class ApplicationInitializer(
    private val application: Application,
    private val configurationRepository: ConfigurationRepository
) {

    fun initialiseLogging() {
        if (configurationRepository.isDebugEnabled) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber initialised.")
        } else {
            Log.d(this::class.simpleName, "Skipped Timber initialisation.")
        }
    }

    fun initialiseStetho() {
        if (configurationRepository.isDebugEnabled) {
            Log.d(this::class.simpleName, "Stetho initialised.")
            Stetho.initializeWithDefaults(application)
        } else {
            Log.d(this::class.simpleName, "Skipped Stetho initialisation.")
        }
    }

}