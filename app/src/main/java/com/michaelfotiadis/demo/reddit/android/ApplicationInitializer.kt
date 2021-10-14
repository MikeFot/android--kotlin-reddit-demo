package com.michaelfotiadis.demo.reddit.android

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.michaelfotiadis.demo.reddit.android.repository.ConfigurationRepository
import timber.log.Timber
import com.facebook.flipper.plugins.inspector.DescriptorMapping

import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils

import com.facebook.flipper.core.FlipperClient
import com.facebook.soloader.SoLoader


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

    fun initialiseFlipper() {
        if (configurationRepository.isDebugEnabled && FlipperUtils.shouldEnableFlipper(application)) {
            SoLoader.init(application, false)
            with(AndroidFlipperClient.getInstance(application)) {
                addPlugin(InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()))
                start()
            }
            Log.d(this::class.simpleName, "Flipper initialised.")
        } else {
            Log.d(this::class.simpleName, "Skipped Flipper initialisation.")
        }

    }

}